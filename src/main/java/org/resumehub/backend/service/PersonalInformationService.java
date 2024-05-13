package org.resumehub.backend.service;

import org.resumehub.backend.dto.PersonalInformationDTO;

import java.util.List;

public interface PersonalInformationService {

    List<PersonalInformationDTO> getAllPersonalInformation();

    PersonalInformationDTO savePersonalInformation(PersonalInformationDTO personalInformation);

    PersonalInformationDTO getPersonalInformationById(String personalInformationId);

    PersonalInformationDTO updatePersonalInformation(String personalInformationId, PersonalInformationDTO updatedPersonalInformation);

    void deletePersonalInformation(String personalInformationId);
}
