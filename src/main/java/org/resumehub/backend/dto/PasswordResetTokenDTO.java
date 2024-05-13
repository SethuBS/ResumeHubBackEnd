package org.resumehub.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PasswordResetTokenDTO {
    private String id;
    @NotNull
    private String resetToken;
    @NotNull
    private Date tokenExpiry;
    @NotNull
    private String userId;
}
