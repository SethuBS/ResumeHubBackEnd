package org.resumehub.backend.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "password_reset_tokens")
public class PasswordResetToken {
    @Id
    private String id;
    private String resetToken;
    private Date tokenExpiry;
    private String userId;

    public PasswordResetToken(String resetToken, Date tokenExpiry, String userId) {
        this.resetToken = resetToken;
        this.tokenExpiry = tokenExpiry;
        this.userId = userId;
    }

}
