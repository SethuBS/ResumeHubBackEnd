package org.resumehub.backend.map;

import org.resumehub.backend.dto.PersonalInformationDto;
import org.resumehub.backend.entity.PersonalInformation;

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
}
