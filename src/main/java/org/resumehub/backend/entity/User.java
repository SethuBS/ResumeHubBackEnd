package org.resumehub.backend.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String fullName;
    private String email;
    private String password;
    private String role;
    // Method to mask the password for logging purposes
    public String getMaskedPassword() {
        int passwordLength = password.length();
        return "*".repeat(passwordLength);
    }

}
