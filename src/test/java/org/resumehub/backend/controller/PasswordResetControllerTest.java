package org.resumehub.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.resumehub.backend.EmailConfig.EmailConfiguration;
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
import static org.mockito.ArgumentMatchers.eq;
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

    @Mock
    private EmailConfiguration emailConfiguration;


    @BeforeEach
    @Deprecated
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.passwordResetController = new PasswordResetController(userService, passwordResetTokenService, emailService, emailConfiguration);

    }

    @Test
    void forgotPassword_shouldSendResetTokenEmail() {
        // Given
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", "johndoe@example.com");

        UserDTO userDTO = new UserDTO(
                "66462a21c54da04178135ac7",
                "John Doe",
                "johndoe@example.com",
                "password",
                "Admin"
        );

        PasswordResetTokenDTO passwordResetTokenDTO = new PasswordResetTokenDTO(
                "664c73f16279487ca0c429f4",
                "66462a21c54da04178135ac7",
                new Date(),
                "66462a21c54da04178135ac7"
        );

        when(userService.getUserByEmail("johndoe@example.com")).thenReturn(userDTO);
        when(passwordResetTokenService.generateResetToken("66462a21c54da04178135ac7")).thenReturn(passwordResetTokenDTO);
        when(emailConfiguration.getResetPasswordSubject()).thenReturn("Reset Your Password - ResumeHub");
        when(emailConfiguration.getResetPasswordBody()).thenReturn("Reset password body with token link: http://localhost:3000/reset-password?token=66462a21c54da04178135ac7");

        // When
        ResponseEntity<?> response = passwordResetController.forgotPassword(requestBody);

        // Then
        verify(emailService).sendEmail(eq("johndoe@example.com"), eq("Reset Your Password - ResumeHub"), eq("John Doe"), eq("Reset password body with token link: http://localhost:3000/reset-password?token=66462a21c54da04178135ac7"));
        assertEquals(ResponseEntity.ok("Password reset token sent to your email"), response);
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
