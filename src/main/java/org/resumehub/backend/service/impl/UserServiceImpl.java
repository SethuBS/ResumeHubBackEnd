package org.resumehub.backend.service.impl;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.resumehub.backend.dto.UserDto;
import org.resumehub.backend.exception.ResourceAlreadyExistsException;
import org.resumehub.backend.exception.ResourceNotFoundException;
import org.resumehub.backend.map.Mapper;
import org.resumehub.backend.repository.UserRepository;
import org.resumehub.backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    @Override
    public List<UserDto> findAll() {
        var listOfUsers = userRepository.findAll()
                .stream().map(Mapper::mapToDto)
                .collect(Collectors.toList());
        // Mask the passwords before logging
        listOfUsers.forEach(userDto -> userDto.setPassword(userDto.getMaskedPassword()));
        logger.info("List of users: {}", listOfUsers);
        return listOfUsers;
    }

    @Override
    public UserDto findById(String userId) {
        var oneRecordOfUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with given id: " + userId + " does not exist in the system"));
        // Mask the passwords before logging
        oneRecordOfUser.setPassword(oneRecordOfUser.getMaskedPassword());
        logger.info("One record of user: {}", oneRecordOfUser);
        return Mapper.mapToDto(oneRecordOfUser);
    }

    @Override
    public UserDto create(UserDto newUser) {
        String email = newUser.getEmail();

        var existingUser = userRepository.findUserByEmail(email);

        Optional.ofNullable(existingUser)
                .ifPresent(user -> {
                    throw new ResourceAlreadyExistsException("User with given email address: " + email + " already exists in the system");
                });
        var userToBeAdded = Mapper.mapToEntity(newUser);
        var addedUser = userRepository.save(userToBeAdded);
        // Mask the passwords before logging
        addedUser.setPassword(addedUser.getMaskedPassword());
        logger.info("Saved record of User: {}", addedUser);

        return Mapper.mapToDto(addedUser);
    }

    @Override
    public UserDto update(String userId, UserDto updatedUser) {
        var oneRecordOfExistingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with given id: " + userId + " does not exist in the system"));

        oneRecordOfExistingUser.setEmail(updatedUser.getEmail());
        oneRecordOfExistingUser.setPassword(updatedUser.getPassword());
        userRepository.save(oneRecordOfExistingUser);
        // Mask the passwords before logging
        oneRecordOfExistingUser.setPassword(oneRecordOfExistingUser.getMaskedPassword());
        logger.info("Updated record of personal information: {}", oneRecordOfExistingUser);

        return Mapper.mapToDto(oneRecordOfExistingUser);
    }

    @Override
    public void delete(String userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with given id: " + userId + " does not exist in the system"));
        logger.info("Deleted record of user: {}", userRepository.findById(userId));
        userRepository.deleteById(userId);

    }
}
