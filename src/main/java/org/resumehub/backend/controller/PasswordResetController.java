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

        // Send reset password link in email
        var resetPasswordLink = "http://localhost:8080/recovery/reset-password?token=" + passwordResetToken.getResetToken();
        emailService.sendEmail(userDTO.getEmail(),
                "Reset Your Password - ResumeHub",
                userDTO.getFullName(), "<p>You recently requested to reset your password for your ResumeHub account. To complete the process, please click on the link below:</p>"
                        + resetPasswordLink + "<p>If you did not request this, please ignore this email. Your password will not be changed unless you click the link above to reset it." +
                        "For security reasons, this link will expire in 24 hours.<p/>" +
                        "<p>If you need further assistance or have any questions, please feel free to contact us at sethuserge@gmail.com. Our team is always here to help.<p/>" +
                        "\n" +
                        "<p>Best regards,<p/><br/>" +
                        "<p>The ResumeHub Team<p/>");

        return ResponseEntity.ok("Password reset link sent to your email");
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
