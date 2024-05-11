package org.resumehub.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.resumehub.backend.dto.UserDto;
import org.resumehub.backend.exception.ResourceAlreadyExistsException;
import org.resumehub.backend.exception.ResourceNotFoundException;
import org.resumehub.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    @Deprecated
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindAll() {
        // Mock
        List<UserDto> userDtoList = new ArrayList<>();
        userDtoList.add(new UserDto(
                "663fb95b364adc66334cb83a",
                "sethuserge@gmail.com",
                "sethuserge@gmail.com"
        ));

        // Mock service method
        when(userService.findAll()).thenReturn(userDtoList);

        // Call controller method
        ResponseEntity<List<UserDto>> response = userController.findAll();

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDtoList, response.getBody());
    }

    @Test
    public void testFindById() {
        // Mock Data
        var user = new UserDto(
                "663fb95b364adc66334cb83a",
                "sethuserge@gmail.com",
                "sethuserge@gmail.com"
        );

        // Mocking userService.findById(user.getId()) to return UserDto
        when(userService.findById(user.getId())).thenReturn(user);

        // Call the Controller method
        ResponseEntity<UserDto> userDtoResponseEntity = userController.findById(user.getId());

        // Verify the response entity
        assertEquals(HttpStatus.OK, userDtoResponseEntity.getStatusCode());
        assertEquals(user, userDtoResponseEntity.getBody());

    }

    @Test
    public void testFindById_ResourceNotFoundException() {
        // Mock data
        String userId = "663d89525a32f82254013cb9101";

        // Mocking userService.findById(userId) to throw a ResourceNotFoundException
        when(userService.findById(userId)).thenThrow(new ResourceNotFoundException("User not found"));

        // Call the controller method and assert that ResourceNotFundException is thrown
        assertThrows(ResourceNotFoundException.class, () -> userController.findById(userId));
    }

    @Test
    public void testCreate() {
        // Mock Data
        var user = new UserDto(
                "663fb95b364adc66334cb83a",
                "sethuserge@gmail.com",
                "sethuserge@gmail.com"
        );

        // Mock the userService's create method to throw a ResourceAlreadyExistsException
        when(userService.create(any(UserDto.class))).thenThrow(new ResourceAlreadyExistsException("User with given email address: " + user.getEmail() + " does not exist"));

        // Verify that the exception is thrown
        assertThrows(ResourceAlreadyExistsException.class, () -> userController.create(user));

    }

    @Test
    public void testCreate_ResourceAlreadyExists() {
        // Mock Data
        var user = new UserDto(
                "663fb95b364adc66334cb83a",
                "sethuserge@gmail.com",
                "sethuserge@gmail.com"
        );

        // Mock the userService's create method to throw a ResourceAlreadyExistsException
        when(userService.create(any(UserDto.class))).thenThrow(new ResourceAlreadyExistsException("User with given email address: " + user.getEmail() + " already exists in the system"));

        // Verify that the exception is thrown
        assertThrows(ResourceAlreadyExistsException.class, () -> userController.create(user));
    }

    @Test
    public void testUpdate() {
        // Mock Data
        String userId = "663d89525a32f82254013cb9";
        var user = new UserDto(
                "663fb95b364adc66334cb83a",
                "sethuserge@gmail.com",
                "sethuserge@gmail.com"
        );

        // When
        when(userService.update(userId, user)).thenReturn(new UserDto());

        // Then
        ResponseEntity<UserDto> response = userController.update(userId, user);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testUpdate_ResourceNotFoundException() {
        // Given
        String userId = "663d89525a32f82254013cb9";

        var user = new UserDto();

        // When
        when(userService.update(userId, user)).thenThrow(new ResourceNotFoundException("Resource not found"));

        // Then
        assertThrows(ResourceNotFoundException.class, () -> userController.update(userId, user));
    }

    @Test
    public void testDelete() {
        // Given
        String userId = "663d89525a32f82254013cb9";

        // When
        ResponseEntity<String> stringResponseEntity = userController.delete(userId);

        // Then
        assertEquals(HttpStatus.OK, stringResponseEntity.getStatusCode());
        assertEquals("User deleted successful", stringResponseEntity.getBody());
        verify(userService, times(1)).delete(userId);

    }
}
