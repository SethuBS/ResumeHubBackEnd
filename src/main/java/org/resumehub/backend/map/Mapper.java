package org.resumehub.backend.map;

import org.resumehub.backend.dto.*;
import org.resumehub.backend.entity.*;

public class Mapper {

    public static PersonalInformationDTO mapToDto(PersonalInformation personalInformation) {
        return new PersonalInformationDTO(
                personalInformation.getId(),
                personalInformation.getUserId(),
                personalInformation.getFullName(),
                personalInformation.getPosition(),
                personalInformation.getCompany(),
                personalInformation.getCity(),
                personalInformation.getProvince(),
                personalInformation.getCountry(),
                personalInformation.getMobile(),
                personalInformation.getEmail(),
                personalInformation.getLinkedIn()
        );
    }

    public static PersonalInformation mapToEntity(PersonalInformationDTO personalInformationDto) {
        return new PersonalInformation(
                personalInformationDto.getId(),
                personalInformationDto.getUserId(),
                personalInformationDto.getFullName(),
                personalInformationDto.getPosition(),
                personalInformationDto.getCompany(),
                personalInformationDto.getCity(),
                personalInformationDto.getProvince(),
                personalInformationDto.getCountry(),
                personalInformationDto.getMobile(),
                personalInformationDto.getEmail(),
                personalInformationDto.getLinkedIn()
        );
    }

    public static WorkExperienceDTO mapToDto(WorkExperience workExperience) {
        return new WorkExperienceDTO(
                workExperience.getId(),
                workExperience.getUserId(),
                workExperience.getCompanyName(),
                workExperience.getPosition(),
                workExperience.getStartDate(),
                workExperience.getEndDate(),
                workExperience.getCity(),
                workExperience.getProvince(),
                workExperience.getCountry(),
                workExperience.getResponsibilities(),
                workExperience.getSkills()
        );
    }

    public static WorkExperience mapToEntity(WorkExperienceDTO workExperienceDto) {
        return new WorkExperience(
                workExperienceDto.getId(),
                workExperienceDto.getUserId(),
                workExperienceDto.getCompanyName(),
                workExperienceDto.getPosition(),
                workExperienceDto.getStartDate(),
                workExperienceDto.getEndDate(),
                workExperienceDto.getCity(),
                workExperienceDto.getProvince(),
                workExperienceDto.getCountry(),
                workExperienceDto.getResponsibilities(),
                workExperienceDto.getSkills()
        );
    }

    public static EducationDTO mapToDto(Education education) {
        return new EducationDTO(
                education.getId(),
                education.getUserId(),
                education.getInstitution(),
                education.getDegree(),
                education.getFieldOfStudy(),
                education.getStartDate(),
                education.getEndDate()
        );
    }

    public static Education mapTOEntity(EducationDTO educationDto) {
        return new Education(
                educationDto.getId(),
                educationDto.getUserId(),
                educationDto.getInstitution(),
                educationDto.getDegree(),
                educationDto.getFieldOfStudy(),
                educationDto.getStartDate(),
                educationDto.getEndDate()
        );
    }

    public static ReferenceDTO mapToDto(Reference reference) {
        return new ReferenceDTO(
                reference.getId(),
                reference.getUserId(),
                reference.getName(),
                reference.getCompany(),
                reference.getEmail(),
                reference.getMobile()
        );
    }

    public static Reference mapToEntity(ReferenceDTO referenceDto) {
        return new Reference(
                referenceDto.getId(),
                referenceDto.getUserId(),
                referenceDto.getName(),
                referenceDto.getCompany(),
                referenceDto.getEmail(),
                referenceDto.getMobile()
        );
    }

    public static UserDTO mapToDto(User user) {
        return new UserDTO(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }

    public static User mapToEntity(UserDTO userDto) {
        return new User(
                userDto.getId(),
                userDto.getFullName(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getRole()
        );
    }

    public static User mapToEntity(LoginDTO loginDTO) {
        return new User(
                loginDTO.getId(),
                loginDTO.getFullName(),
                loginDTO.getEmail(),
                loginDTO.getPassword(),
                loginDTO.getRole()
        );
    }

    public static PasswordResetTokenDTO mapToDto(PasswordResetToken passwordResetToken) {
        return new PasswordResetTokenDTO(
                passwordResetToken.getId(),
                passwordResetToken.getResetToken(),
                passwordResetToken.getTokenExpiry(),
                passwordResetToken.getUserId()
        );
    }
}
