package org.resumehub.backend.controller;

import lombok.AllArgsConstructor;
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

    private final UserService userService;

    private final PasswordResetTokenService passwordResetTokenService;

    private EmailService emailService;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Validated @RequestBody Map<String, String> email) {
        var userEmail = email.get("email");
        var userDTO = userService.getUserByEmail(userEmail);

        var passwordResetToken = passwordResetTokenService.generateResetToken(userDTO.getId());

        // Send reset password token in email
        var resetPasswordLinkToken = passwordResetToken.getResetToken();
        emailService.sendEmail(userDTO.getEmail(),
                "Reset Your Password - ResumeHub",
                userDTO.getFullName(), "<body><p>Copy the following token, go to reset password page and use it to reset your password:<p/> " + resetPasswordLinkToken + " <p>Best regards,<p/><p>The ResumeHub Team<p/><body/>");

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
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }
    }

}
