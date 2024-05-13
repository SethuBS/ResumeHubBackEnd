package org.resumehub.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WorkExperienceDTO {

    private String id;
    @NotNull
    private String userId;
    @NotNull
    private String companyName;
    @NotNull
    private String position;
    @NotNull
    private String startDate;
    @NotNull
    private String endDate;
    @NotNull
    private String city;
    @NotNull
    private String province;
    @NotNull
    private String country;
    @NotNull
    private List<String> responsibilities;
    @NotNull
    private List<String> skills;
}
