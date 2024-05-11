package org.resumehub.backend.service.impl;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.resumehub.backend.dto.WorkExperienceDto;
import org.resumehub.backend.exception.ResourceNotFoundException;
import org.resumehub.backend.map.Mapper;
import org.resumehub.backend.repository.WorkExperienceRepository;
import org.resumehub.backend.service.WorkExperienceService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class WorkExperienceServiceImpl implements WorkExperienceService {

    private static final Logger logger = LogManager.getLogger(WorkExperienceServiceImpl.class);

    private final WorkExperienceRepository workExperienceRepository;

    @Override
    public List<WorkExperienceDto> getAllWorkExperience() {
        var listOfWorkExperience = workExperienceRepository.findAll()
                .stream().map(Mapper::mapToDto)
                .collect(Collectors.toList());
        logger.info("List of work experience: {}", listOfWorkExperience);
        return listOfWorkExperience;
    }

    @Override
    public WorkExperienceDto getWorkExperienceById(String workExperienceId) {
        var workExperience = workExperienceRepository.findById(workExperienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Work Experience with given id: " + workExperienceId + " does not exist"));
        logger.info("One record of work experience: {}", workExperience);
        return Mapper.mapToDto(workExperience);
    }

    @Override
    public WorkExperienceDto saveWorkExperience(WorkExperienceDto workExperience) {
        var workExperienceToSave = Mapper.mapToEntity(workExperience);
        var savedWorkExperience = workExperienceRepository.save(workExperienceToSave);
        logger.info("Saved record of work experience: {}", workExperienceToSave);
        return Mapper.mapToDto(savedWorkExperience);
    }

    @Override
    public WorkExperienceDto updateWorkExperience(String workExperienceId, WorkExperienceDto updatedWorkExperience) {
        var workExperience = workExperienceRepository.findById(workExperienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Work Experience with given id: " + workExperienceId + " does not exist"));

        workExperience.setUserId(updatedWorkExperience.getUserId());
        workExperience.setCompanyName(updatedWorkExperience.getCompanyName());
        workExperience.setPosition(updatedWorkExperience.getPosition());
        workExperience.setStartDate(updatedWorkExperience.getStartDate());
        workExperience.setEndDate(updatedWorkExperience.getEndDate());
        workExperience.setCity(updatedWorkExperience.getCity());
        workExperience.setProvince(updatedWorkExperience.getProvince());
        workExperience.setCountry(updatedWorkExperience.getCountry());
        workExperience.setResponsibilities(updatedWorkExperience.getResponsibilities());
        workExperience.setSkills(updatedWorkExperience.getSkills());
        logger.info("Updated record of work experience: {}", workExperience);
        workExperienceRepository.save(workExperience);
        return Mapper.mapToDto(workExperience);
    }

    @Override
    public void deleteWorkExperience(String workExperienceId) {
        workExperienceRepository.findById(workExperienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Work Experience with given id: " + workExperienceId + " does not exist"));
        logger.info("Deleted record of work experience: {}", workExperienceRepository.findById(workExperienceId));
        workExperienceRepository.deleteById(workExperienceId);
    }
}
