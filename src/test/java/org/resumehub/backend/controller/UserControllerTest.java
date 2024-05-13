package org.resumehub.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.resumehub.backend.dto.UserDTO;
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

    private final String authorization = "eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE3MTU1NTA3OTEsImV4cCI6MTcxNTYzNzE5MSwiZW1haWwiOiJzZXRodXNlcmdlQHlhaG9vLmNvbSIsImF1dGhvcml0aWVzIjoiIn0.lGi6KXPSEmlrpSUaAEpWc6nbek8idH_JXUpMIDDmZ72QmGzVPqJXHgJW4hPlpt3Z";
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
    void testGetUserProfile() {
        String jwt = "sampleJwtToken";
        var expectedUserProfile = new UserDTO(
                "663fb95b364adc66334cb83a",
                "Sethu Serge Budaza",
                "sethuserge@gmail.com",
                "sethuserge@gmail.com",
                "ROLE_CUSTOMER"
        );

        when(userService.findUserProfileByJwt(jwt)).thenReturn(expectedUserProfile);

        UserDTO userProfile = userController.getUserProfile(jwt).getBody();

        assertEquals(expectedUserProfile, userProfile);
    }


    @Test
    public void testFindAll() {
        // Mock
        List<UserDTO> userDTOList = new ArrayList<>();
        userDTOList.add(new UserDTO(
                "663fb95b364adc66334cb83a",
                "Sethu Serge Budaza",
                "sethuserge@gmail.com",
                "sethuserge@gmail.com",
                "ROLE_CUSTOMER"
        ));

        // Mock service method
        when(userService.findAll()).thenReturn(userDTOList);

        // Call controller method
        ResponseEntity<List<UserDTO>> response = userController.findAll(authorization);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDTOList, response.getBody());
    }

    @Test
    public void testFindById() {
        // Mock Data
        var user = new UserDTO(
                "663fb95b364adc66334cb83a",
                "Sethu Serge Budaza",
                "sethuserge@gmail.com",
                "sethuserge@gmail.com",
                "ROLE_CUSTOMER"
        );

        // Mocking userService.findById(user.getId()) to return UserDTO
        when(userService.findById(user.getId())).thenReturn(user);

        // Call the Controller method
        ResponseEntity<UserDTO> userDtoResponseEntity = userController.findById(authorization, user.getId());

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
        assertThrows(ResourceNotFoundException.class, () -> userController.findById(authorization, userId));
    }

    @Test
    public void testCreate() {
        // Mock Data
        var user = new UserDTO(
                "663fb95b364adc66334cb83a",
                "Sethu Serge Budaza",
                "sethuserge@gmail.com",
                "sethuserge@gmail.com",
                "ROLE_CUSTOMER"
        );

        // Mock the userService's create method to throw a ResourceAlreadyExistsException
        when(userService.create(any(UserDTO.class))).thenThrow(new ResourceAlreadyExistsException("User with given email address: " + user.getEmail() + " does not exist"));

        // Verify that the exception is thrown
        assertThrows(ResourceAlreadyExistsException.class, () -> userController.create(authorization, user));

    }

    @Test
    public void testCreate_ResourceAlreadyExists() {
        // Mock Data
        var user = new UserDTO(
                "663fb95b364adc66334cb83a",
                "Sethu Serge Budaza",
                "sethuserge@gmail.com",
                "sethuserge@gmail.com",
                "ROLE_CUSTOMER"
        );

        // Mock the userService's create method to throw a ResourceAlreadyExistsException
        when(userService.create(any(UserDTO.class))).thenThrow(new ResourceAlreadyExistsException("User with given email address: " + user.getEmail() + " already exists in the system"));

        // Verify that the exception is thrown
        assertThrows(ResourceAlreadyExistsException.class, () -> userController.create(authorization, user));
    }

    @Test
    public void testUpdate() {
        // Mock Data
        String userId = "663d89525a32f82254013cb9";
        var user = new UserDTO(
                "663fb95b364adc66334cb83a",
                "Sethu Serge Budaza",
                "sethuserge@gmail.com",
                "sethuserge@gmail.com",
                "ROLE_CUSTOMER"
        );

        // When
        when(userService.update(userId, user)).thenReturn(new UserDTO());

        // Then
        ResponseEntity<UserDTO> response = userController.update(authorization, userId, user);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    public void testUpdate_ResourceNotFoundException() {
        // Given
        String userId = "663d89525a32f82254013cb9";

        var user = new UserDTO();

        // When
        when(userService.update(userId, user)).thenThrow(new ResourceNotFoundException("Resource not found"));

        // Then
        assertThrows(ResourceNotFoundException.class, () -> userController.update(authorization, userId, user));
    }

    @Test
    public void testDelete() {
        // Given
        String userId = "663d89525a32f82254013cb9";

        // When
        ResponseEntity<String> stringResponseEntity = userController.delete(authorization, userId);

        // Then
        assertEquals(HttpStatus.OK, stringResponseEntity.getStatusCode());
        assertEquals("User deleted successful", stringResponseEntity.getBody());
        verify(userService, times(1)).delete(userId);

    }
}
