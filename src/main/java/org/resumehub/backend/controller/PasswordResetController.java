package org.resumehub.backend.controller;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.resumehub.backend.EmailConfig.EmailConfiguration;
import org.resumehub.backend.service.EmailService;
import org.resumehub.backend.service.PasswordResetTokenService;
import org.resumehub.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/recovery")
public class PasswordResetController {

    private static final Logger logger = LogManager.getLogger(PasswordResetController.class);

    private final UserService userService;

    private final PasswordResetTokenService passwordResetTokenService;

    private final EmailService emailService;

    private final EmailConfiguration emailConfiguration;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Validated @RequestBody Map<String, String> email) {
        var userEmail = email.get("email");
        var userDTO = userService.getUserByEmail(userEmail);

        var passwordResetToken = passwordResetTokenService.generateResetToken(userDTO.getId());
        var resetLink = "http://localhost:3000/reset-password?token=" + passwordResetToken.getResetToken();

        String body = String.format(emailConfiguration.getResetPasswordBody(), resetLink);

        logger.info("Email Body: {}", body);

        // Send reset password token in email
        emailService.sendEmail(
                userDTO.getEmail(),
                emailConfiguration.getResetPasswordSubject(),
                userDTO.getFullName(),
                body
        );

        return ResponseEntity.ok("Password reset token sent to your email");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Validated @RequestParam String token, @RequestBody Map<String, String> password) {
        var newPassword = password.get("password");
        var passwordResetToken = passwordResetTokenService.getResetToken(token);

        if (passwordResetToken != null) {
            userService.updateUserPassword(passwordResetToken.getUserId(), newPassword);
            passwordResetTokenService.deleteResetToken(token);
            return ResponseEntity.ok("Password reset successfully");
        }

        return ResponseEntity.badRequest().body("Invalid or expired token");
    }

}
