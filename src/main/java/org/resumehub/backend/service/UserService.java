package org.resumehub.backend.service;

import org.resumehub.backend.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();

    UserDto findById(String userId);

    UserDto create(UserDto newUser);

    UserDto update(String userId, UserDto updatedUser);

    void delete(String userId);
}
