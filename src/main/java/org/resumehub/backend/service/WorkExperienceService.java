package org.resumehub.backend.service;

import org.resumehub.backend.dto.WorkExperienceDto;

import java.util.List;

public interface WorkExperienceService {
    List<WorkExperienceDto> getAllWorkExperience();

    WorkExperienceDto getWorkExperienceById(String workExperienceId);

    WorkExperienceDto saveWorkExperience(WorkExperienceDto workExperience);

    WorkExperienceDto updateWorkExperience(String workExperienceId, WorkExperienceDto updatedWorkExperience);

    void deleteWorkExperience(String workExperienceId);
}
