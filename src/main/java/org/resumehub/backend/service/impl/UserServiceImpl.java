package org.resumehub.backend.service.impl;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.resumehub.backend.EmailConfig.EmailConfiguration;
import org.resumehub.backend.SecurityConfig.JwtProvider;
import org.resumehub.backend.dto.LoginDTO;
import org.resumehub.backend.dto.UserDTO;
import org.resumehub.backend.enums.UserRole;
import org.resumehub.backend.exception.ResourceAlreadyExistsException;
import org.resumehub.backend.exception.ResourceNotFoundException;
import org.resumehub.backend.map.Mapper;
import org.resumehub.backend.repository.UserRepository;
import org.resumehub.backend.response.AuthResponse;
import org.resumehub.backend.service.EmailService;
import org.resumehub.backend.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final EmailConfiguration emailConfiguration;
    private final EmailService emailService;
    private PasswordEncoder passwordEncoder;

    private UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        logger.info("User loaded by username(email address) : {}", user);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities);

    }

    @Override
    public UserDTO findUserProfileByJwt(String jwt) {
        var email = JwtProvider.getEmailFromJwtToken(jwt);
        var user = userRepository.findUserByEmail(email);

        if (user == null) {
            throw new ResourceNotFoundException("User with a given email: " + email + " does not exist in the system");
        }

        return Mapper.mapToDto(user);
    }

    @Override
    public AuthResponse loginUser(LoginDTO loginDTO) {
        var username = loginDTO.getEmail();
        var password = loginDTO.getPassword();

        var maskedPassword = "*".repeat(password.length());
        logger.info("User credentials: {}", username + " " + maskedPassword);

        var authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var token = JwtProvider.generateToken(authentication);
        var authResponse = new AuthResponse();

        authResponse.setMessage("Login success");
        authResponse.setToken(token);
        authResponse.setStatus(true);

        var loggedInUser = Mapper.mapToEntity(loginDTO);
        logger.info("Logged in user: {}", loggedInUser);
        return authResponse;
    }

    private Authentication authenticate(String username, String password) {

        logger.info("Username: {} Password: {}", username, password);

        var userDetails = loadUserByUsername(username);

        logger.info("Sign in in user details: {}", userDetails);

        if (!passwordEncoder.matches(password.trim(), userDetails.getPassword())) {
            logger.info("password size , userDetails.getPassword size {}, {}", password.length(), userDetails.getPassword().length());
            logger.info("Sign in userDetails - password mismatch: {}", userDetails);
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }

    @Override
    public List<UserDTO> findAll() {
        var listOfUsers = userRepository.findAll()
                .stream().map(Mapper::mapToDto)
                .collect(Collectors.toList());
        // Mask the passwords before logging
        listOfUsers.forEach(userDto -> userDto.setPassword(userDto.getMaskedPassword()));
        logger.info("List of users: {}", listOfUsers);
        return listOfUsers;
    }

    @Override
    public UserDTO findById(String userId) {
        var oneRecordOfUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with given id: " + userId + " does not exist in the system"));

        logger.info("One record of user: {}", oneRecordOfUser);
        return Mapper.mapToDto(oneRecordOfUser);
    }

    @Override
    public UserDTO create(UserDTO newUser) {
        var email = newUser.getEmail();
        var password = newUser.getPassword();

        var existingUser = userRepository.findUserByEmail(email);

        Optional.ofNullable(existingUser)
                .ifPresent(user -> {
                    throw new ResourceAlreadyExistsException("User with given email address: " + email + " already exists in the system");
                });
        var userToBeAdded = Mapper.mapToEntity(newUser);
        // Encode the password before saving
        var encodedPassword = passwordEncoder.encode(password);
        var userRole = UserRole.ADMIN.getRole();
        logger.info("You don't wanna know: {}", encodedPassword);
        userToBeAdded.setPassword(encodedPassword);
        userToBeAdded.setRole(userRole);
        var addedUser = userRepository.save(userToBeAdded);
        // Welcome email
        emailService.sendEmail(addedUser.getEmail(), emailConfiguration.getEmailSubject(), addedUser.getFullName(), emailConfiguration.getEmailBody());
        Authentication authentication = new UsernamePasswordAuthenticationToken(addedUser.getEmail(), addedUser.getEmail());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var token = JwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setMessage("Register Success");
        authResponse.setStatus(true);

        // Mask the passwords before logging
        addedUser.setPassword(addedUser.getMaskedPassword());
        logger.info("Saved record of User: {}", addedUser);

        return Mapper.mapToDto(addedUser);
    }

    @Override
    public UserDTO update(String userId, UserDTO updatedUser) {
        var oneRecordOfExistingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with given id: " + userId + " does not exist in the system"));
        var encodedPassword = updatedUser.getPassword();
        var userRole = UserRole.ADMIN.getRole();
        oneRecordOfExistingUser.setFullName(updatedUser.getFullName());
        oneRecordOfExistingUser.setEmail(updatedUser.getEmail());
        oneRecordOfExistingUser.setPassword(updatedUser.getPassword());
        oneRecordOfExistingUser.setRole(userRole);
        oneRecordOfExistingUser.setPassword(encodedPassword);
        userRepository.save(oneRecordOfExistingUser);
        logger.info("Updated record of personal information: {}", oneRecordOfExistingUser);

        return Mapper.mapToDto(oneRecordOfExistingUser);
    }

    @Override
    public void updateUserPassword(String userId, String newPassword) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));

        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        var user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User with given : email" + email + " does not exist");
        }
        return Mapper.mapToDto(user);
    }

    @Override
    public void delete(String userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with given id: " + userId + " does not exist in the system"));
        logger.info("Deleted record of user: {}", userRepository.findById(userId));
        userRepository.deleteById(userId);

    }
}
