package org.resumehub.backend.map;

import org.resumehub.backend.dto.*;
import org.resumehub.backend.entity.*;

public class Mapper {

    public static PersonalInformationDto mapToDto(PersonalInformation personalInformation) {
        return new PersonalInformationDto(
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

    public static PersonalInformation mapToEntity(PersonalInformationDto personalInformationDto) {
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

    public static WorkExperienceDto mapToDto(WorkExperience workExperience) {
        return new WorkExperienceDto(
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

    public static WorkExperience mapToEntity(WorkExperienceDto workExperienceDto) {
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

    public static EducationDto mapToDto(Education education) {
        return new EducationDto(
                education.getId(),
                education.getUserId(),
                education.getInstitution(),
                education.getDegree(),
                education.getFieldOfStudy(),
                education.getStartDate(),
                education.getEndDate()
        );
    }

    public static Education mapTOEntity(EducationDto educationDto) {
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

    public static ReferenceDto mapToDto(Reference reference) {
        return new ReferenceDto(
                reference.getId(),
                reference.getUserId(),
                reference.getName(),
                reference.getCompany(),
                reference.getEmail(),
                reference.getMobile()
        );
    }

    public static Reference mapToEntity(ReferenceDto referenceDto) {
        return new Reference(
                referenceDto.getId(),
                referenceDto.getUserId(),
                referenceDto.getName(),
                referenceDto.getCompany(),
                referenceDto.getEmail(),
                referenceDto.getMobile()
        );
    }

    public static UserDto mapToDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getEmail()
        );
    }

    public static User mapToEntity(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getEmail(),
                userDto.getPassword()
        );
    }
}
