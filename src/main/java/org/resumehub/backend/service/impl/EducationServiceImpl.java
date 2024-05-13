package org.resumehub.backend.service.impl;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.resumehub.backend.dto.EducationDTO;
import org.resumehub.backend.exception.ResourceNotFoundException;
import org.resumehub.backend.map.Mapper;
import org.resumehub.backend.repository.EducationRepository;
import org.resumehub.backend.service.EducationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class EducationServiceImpl implements EducationService {

    private static final Logger logger = LogManager.getLogger(EducationServiceImpl.class);

    private final EducationRepository educationRepository;

    @Override
    public List<EducationDTO> getAllEducation() {
        var listOfEducation = educationRepository.findAll()
                .stream().map(Mapper::mapToDto)
                .collect(Collectors.toList());
        logger.info("List of education {}", listOfEducation);
        return listOfEducation;
    }

    @Override
    public EducationDTO getEducationById(String educationId) {
        var education = educationRepository.findById(educationId)
                .orElseThrow(() -> new ResourceNotFoundException("Education with give id: " + educationId + " does not exist"));
        logger.info("One record of education {}", education);
        return Mapper.mapToDto(education);
    }

    @Override
    public EducationDTO createEducation(EducationDTO newEducation) {
        var education = Mapper.mapTOEntity(newEducation);
        var createdEducation = educationRepository.save(education);
        logger.info("Saved record of education {}", createdEducation);
        return Mapper.mapToDto(createdEducation);
    }

    @Override
    public EducationDTO updateEducation(String educationId, EducationDTO updatedEducation) {
        var education = educationRepository.findById(educationId)
                .orElseThrow(() -> new ResourceNotFoundException("Education with give id: " + educationId + " does not exist"));

        logger.info("Updated record of education {}", education);
        education.setUserId(updatedEducation.getUserId());
        education.setInstitution(updatedEducation.getInstitution());
        education.setDegree(updatedEducation.getDegree());
        education.setFieldOfStudy(updatedEducation.getFieldOfStudy());
        education.setStartDate(updatedEducation.getStartDate());
        education.setEndDate(updatedEducation.getEndDate());
        return Mapper.mapToDto(education);
    }

    @Override
    public void deleteEducation(String educationId) {
        educationRepository.findById(educationId)
                .orElseThrow(() -> new ResourceNotFoundException("Education with give id: " + educationId + " does not exist"));

        logger.info("Deleted record of education {}", educationRepository.findById(educationId));
        educationRepository.deleteById(educationId);
    }
}
