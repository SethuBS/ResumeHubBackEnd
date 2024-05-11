package org.resumehub.backend.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PersonalInformationDto {

    private String id;
    @NotNull
    private String userId;
    @NotNull
    private String fullName;
    @NotNull
    private String position;
    @NotNull
    private String company;
    @NotNull
    private String city;
    @NotNull
    private String province;
    @NotNull
    private String country;

    @NotNull
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Invalid mobile number format")
    private String mobile;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String linkedIn;

}
