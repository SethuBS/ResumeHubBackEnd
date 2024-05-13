package org.resumehub.backend.service;

import org.resumehub.backend.dto.LoginDTO;
import org.resumehub.backend.dto.UserDTO;
import org.resumehub.backend.response.AuthResponse;

import java.util.List;

public interface UserService {

    UserDTO findUserProfileByJwt(String jwt);

    AuthResponse loginUser(LoginDTO loginDTO);

    List<UserDTO> findAll();

    UserDTO findById(String userId);

    UserDTO create(UserDTO newUser);

    UserDTO update(String userId, UserDTO updatedUser);

    void delete(String userId);
}
