package org.resumehub.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EducationDto {

    private String id;
    @NotNull
    private String userId;
    @NotNull
    private String institution;
    @NotNull
    private String degree;
    @NotNull
    private String fieldOfStudy;
    @NotNull
    private String startDate;
    @NotNull
    private String endDate;
}
