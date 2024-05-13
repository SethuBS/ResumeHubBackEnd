package org.resumehub.backend.service;

import org.resumehub.backend.dto.WorkExperienceDTO;

import java.util.List;

public interface WorkExperienceService {
    List<WorkExperienceDTO> getAllWorkExperience();

    WorkExperienceDTO getWorkExperienceById(String workExperienceId);

    WorkExperienceDTO saveWorkExperience(WorkExperienceDTO workExperience);

    WorkExperienceDTO updateWorkExperience(String workExperienceId, WorkExperienceDTO updatedWorkExperience);

    void deleteWorkExperience(String workExperienceId);
}
