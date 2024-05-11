package org.resumehub.backend.service.impl;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.resumehub.backend.dto.PersonalInformationDto;
import org.resumehub.backend.exception.ResourceAlreadyExistsException;
import org.resumehub.backend.exception.ResourceNotFoundException;
import org.resumehub.backend.map.Mapper;
import org.resumehub.backend.repository.PersonalInformationRepository;
import org.resumehub.backend.service.PersonalInformationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PersonalInformationServiceImpl implements PersonalInformationService {

    private static final Logger logger = LogManager.getLogger(PersonalInformationServiceImpl.class);

    private final PersonalInformationRepository personalInformationRepository;


    @Override
    public List<PersonalInformationDto> getAllPersonalInformation() {
        var listOfPersonalInformation = personalInformationRepository.findAll()
                .stream().map(Mapper::mapToDto)
                .collect(Collectors.toList());
        logger.info("List of personal information :{}", listOfPersonalInformation);
        return listOfPersonalInformation;
    }

    @Override
    public PersonalInformationDto savePersonalInformation(PersonalInformationDto personalInformation) {
        var email = personalInformation.getEmail();

        logger.info(email);

        var exists = personalInformationRepository.findByEmail(email);

        if (exists != null) {
            throw new ResourceAlreadyExistsException("Personal Information with given email address: " + email + " already exists in the system");
        }

        var personalInformationToSave = Mapper.mapToEntity(personalInformation);
        var savedPersonalInformation = personalInformationRepository.save(personalInformationToSave);
        logger.info("Saved record of Personal Information: {}", savedPersonalInformation.toString());
        return Mapper.mapToDto(savedPersonalInformation);
    }

    @Override
    public PersonalInformationDto getPersonalInformationById(String personalInformationId) {
        var personalInformation = personalInformationRepository.findById(personalInformationId)
                .orElseThrow(() -> new ResourceNotFoundException("Personal Information with given id: " + personalInformationId + " does not exist"));
        logger.info("One record of personal information: {}", personalInformation);
        return Mapper.mapToDto(personalInformation);
    }

    @Override
    public PersonalInformationDto updatePersonalInformation(String personalInformationId, PersonalInformationDto updatedPersonalInformation) {
        var personalInformation = personalInformationRepository.findById(personalInformationId)
                .orElseThrow(() -> new ResourceNotFoundException("Personal Information with given id: " + personalInformationId + " does not exist"));

        personalInformation.setUserId(updatedPersonalInformation.getUserId());
        personalInformation.setFullName(updatedPersonalInformation.getFullName());
        personalInformation.setCompany(updatedPersonalInformation.getCompany());
        personalInformation.setCity(updatedPersonalInformation.getCity());
        personalInformation.setProvince(updatedPersonalInformation.getProvince());
        personalInformation.setCountry(updatedPersonalInformation.getCountry());
        personalInformation.setMobile(updatedPersonalInformation.getMobile());
        personalInformation.setEmail(updatedPersonalInformation.getEmail());
        personalInformation.setLinkedIn(updatedPersonalInformation.getLinkedIn());
        personalInformationRepository.save(personalInformation);
        logger.info("Updated record of personal information: {}", personalInformation);
        return Mapper.mapToDto(personalInformation);
    }

    @Override
    public void deletePersonalInformation(String personalInformationId) {
        personalInformationRepository.findById(personalInformationId)
                .orElseThrow(() -> new ResourceNotFoundException("Personal Information with given id: " + personalInformationId + " does not exist"));
        logger.info("Deleted record of personal information: {}", personalInformationRepository.findById(personalInformationId));
        personalInformationRepository.deleteById(personalInformationId);
    }
}
