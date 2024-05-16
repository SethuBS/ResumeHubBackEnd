package org.resumehub.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.resumehub.backend.dto.PasswordResetTokenDTO;
import org.resumehub.backend.dto.UserDTO;
import org.resumehub.backend.service.EmailService;
import org.resumehub.backend.service.PasswordResetTokenService;
import org.resumehub.backend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasswordResetControllerTest {

    private PasswordResetController passwordResetController;

    @Mock
    private UserService userService;

    @Mock
    private PasswordResetTokenService passwordResetTokenService;

    @Mock
    private EmailService emailService;

    @BeforeEach
    @Deprecated
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.passwordResetController = new PasswordResetController(userService, passwordResetTokenService, emailService);

    }

    @Test
    void forgotPassword_shouldSendResetTokenEmail() {
        // Given
        Map<String, String> requestBody = new HashMap<>();
        var passwordResetTokenDTO = new PasswordResetTokenDTO(
                "6643a17942cd8b13dbda4bef",
                "709bea35-aca2-4778-8f10-9c475803c98f",
                new Date(),
                "6645458099a4b457a15b8825"
        );

        UserDTO userDTO = new UserDTO(
                "6645458099a4b457a15b8825",
                "John Doe",
                "johndoe@gmail.com",
                "",
                "Admin"

        );

        requestBody.put("email", userDTO.getEmail());

        when(userService.getUserByEmail(userDTO.getEmail())).thenReturn(userDTO);
        when(passwordResetTokenService.generateResetToken(passwordResetTokenDTO.getUserId())).thenReturn(passwordResetTokenDTO);

        // When
        ResponseEntity<?> response = passwordResetController.forgotPassword(requestBody);

        // Then
        verify(emailService).sendEmail(any(), any(), any(), any());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password reset token sent to your email", response.getBody());
    }

    @Test
    void resetPassword_shouldResetPassword() {
        // Given
        String newPassword = "newPassword";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("password", newPassword);
        var passwordResetTokenDTO = new PasswordResetTokenDTO(
                "6643a17942cd8b13dbda4bef",
                "709bea35-aca2-4778-8f10-9c475803c98f",
                new Date(),
                "6645458099a4b457a15b8825"
        );

        when(passwordResetTokenService.getResetToken(passwordResetTokenDTO.getResetToken())).thenReturn(passwordResetTokenDTO);

        // When
        ResponseEntity<?> response = passwordResetController.resetPassword(passwordResetTokenDTO.getResetToken(), requestBody);

        // Then
        verify(userService).updateUserPassword(any(), any());
        verify(passwordResetTokenService).deleteResetToken(any());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password reset successfully", response.getBody());
    }
}
