package org.resumehub.backend.service;

import org.resumehub.backend.dto.PersonalInformationDto;

import java.util.List;

public interface PersonalInformationService {

    List<PersonalInformationDto> getAllPersonalInformation();

    PersonalInformationDto savePersonalInformation(PersonalInformationDto personalInformation);

    PersonalInformationDto getPersonalInformationById(String personalInformationId);

    PersonalInformationDto updatePersonalInformation(String personalInformationId, PersonalInformationDto updatedPersonalInformation);

    void deletePersonalInformation(String personalInformationId);
}
