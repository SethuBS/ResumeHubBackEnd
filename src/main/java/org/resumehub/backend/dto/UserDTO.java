package org.resumehub.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDTO {
    private String id;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String role;

    // Method to mask the password for logging purposes
    public String getMaskedPassword() {
        int passwordLength = password.length();
        return "*".repeat(passwordLength);
    }
}
