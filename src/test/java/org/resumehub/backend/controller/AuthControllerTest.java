package org.resumehub.backend.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.resumehub.backend.dto.LoginDTO;
import org.resumehub.backend.dto.UserDTO;
import org.resumehub.backend.response.AuthResponse;
import org.resumehub.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthController authController;

    @Test
    public void testRegisterUser() {
        // Given
        var userDTO = new UserDTO(
                "663f8e6381558e20f3bb2522",
                "Sethu Serge Budaz",
                "test@example.com",
                "password",
                "ROLE_CUSTOMER"

        );
        // When
        ResponseEntity<String> responseEntity = authController.registerUser(userDTO);

        // Then
        verify(userService, times(1)).create(userDTO);
        assert responseEntity.getStatusCode() == HttpStatus.CREATED;
        assert Objects.equals(responseEntity.getBody(), "User registered successfully");
    }

    @Test
    public void testLoginUser() {
        // Given
        var loginDTO = new LoginDTO(
                "663f8e6381558e20f3bb2522",
                "Sethu Serge Budaz",
                "test@example.com",
                "password",
                "ROLE_CUSTOMER"

        );

        // When
        AuthResponse authenticationResponse = new AuthResponse(
                "token",
                "Login success",
                true
        );
        when(userService.loginUser(loginDTO)).thenReturn(authenticationResponse);
        ResponseEntity<AuthResponse> responseEntity = authController.loginUser(loginDTO);

        // Then
        verify(userService, times(1)).loginUser(loginDTO);
        assert responseEntity.getStatusCode() == HttpStatus.OK;
        assert responseEntity.getBody().getToken().equals("token");
    }
}
