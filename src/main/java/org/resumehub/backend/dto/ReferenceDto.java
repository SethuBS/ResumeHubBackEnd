package org.resumehub.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReferenceDto {

    private String id;
    @NotNull
    private String userId;
    @NotNull
    private String name;
    @NotNull
    private String company;
    @NotNull
    private String email;
    @NotNull
    private String mobile;
}
