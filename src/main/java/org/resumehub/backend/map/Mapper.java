package org.resumehub.backend.map;

import org.resumehub.backend.dto.PersonalInformationDto;
import org.resumehub.backend.dto.WorkExperienceDto;
import org.resumehub.backend.entity.PersonalInformation;
import org.resumehub.backend.entity.WorkExperience;

public class Mapper {

    public static PersonalInformationDto mapToDto(PersonalInformation personalInformation) {
        return new PersonalInformationDto(
                personalInformation.getId(),
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
                workExperience.getCompanyName(),
                workExperience.getPosition(),
                workExperience.getStartDate(),
                workExperience.getEndDate(),
                workExperience.getCity(),
                workExperience.getProvince(),
                workExperience.getCountry(),
                workExperience.getResponsibilities()
        );
    }

    public static WorkExperience mapToEntity(WorkExperienceDto workExperienceDto) {
        return new WorkExperience(
                workExperienceDto.getId(),
                workExperienceDto.getCompanyName(),
                workExperienceDto.getPosition(),
                workExperienceDto.getStartDate(),
                workExperienceDto.getEndDate(),
                workExperienceDto.getCity(),
                workExperienceDto.getProvince(),
                workExperienceDto.getCountry(),
                workExperienceDto.getResponsibilities()
        );
    }
}
