package org.resumehub.backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperienceDto {

    private String id;
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
}
