package org.resumehub.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginDTO {
    private String id;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String role = "ROLE_CUSTOMER";

}
