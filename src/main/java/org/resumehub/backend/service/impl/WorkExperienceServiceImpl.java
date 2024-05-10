package org.resumehub.backend.service.impl;

import lombok.AllArgsConstructor;
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

    private final WorkExperienceRepository workExperienceRepository;

    @Override
    public List<WorkExperienceDto> getAllWorkExperience() {
        return workExperienceRepository.findAll()
                .stream().map(Mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public WorkExperienceDto getWorkExperienceById(String workExperienceId) {
        var workExperience = workExperienceRepository.findById(workExperienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Work Experience with given id: " + workExperienceId + " does not exist"));
        return Mapper.mapToDto(workExperience);
    }

    @Override
    public WorkExperienceDto saveWorkExperience(WorkExperienceDto workExperience) {
        var workExperienceToSave = Mapper.mapToEntity(workExperience);
        var savedWorkExperience = workExperienceRepository.save(workExperienceToSave);
        return Mapper.mapToDto(savedWorkExperience);
    }

    @Override
    public WorkExperienceDto updateWorkExperience(String workExperienceId, WorkExperienceDto updatedWorkExperience) {
        var workExperience = workExperienceRepository.findById(workExperienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Work Experience with given id: " + workExperienceId + " does not exist"));

        workExperience.setCompanyName(updatedWorkExperience.getCompanyName());
        workExperience.setPosition(updatedWorkExperience.getPosition());
        workExperience.setStartDate(updatedWorkExperience.getStartDate());
        workExperience.setEndDate(updatedWorkExperience.getEndDate());
        workExperience.setCity(updatedWorkExperience.getCity());
        workExperience.setProvince(updatedWorkExperience.getProvince());
        workExperience.setCountry(updatedWorkExperience.getCountry());
        workExperience.setResponsibilities(updatedWorkExperience.getResponsibilities());
        workExperienceRepository.save(workExperience);
        return Mapper.mapToDto(workExperience);
    }

    @Override
    public void deleteWorkExperience(String workExperienceId) {
        workExperienceRepository.findById(workExperienceId)
                .orElseThrow(() -> new ResourceNotFoundException("Work Experience with given id: " + workExperienceId + " does not exist"));
        workExperienceRepository.deleteById(workExperienceId);
    }
}
