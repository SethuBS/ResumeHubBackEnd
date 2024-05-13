package org.resumehub.backend.service;

import org.resumehub.backend.dto.EducationDTO;

import java.util.List;

public interface EducationService {
    List<EducationDTO> getAllEducation();

    EducationDTO getEducationById(String educationId);

    EducationDTO createEducation(EducationDTO newEducation);

    EducationDTO updateEducation(String educationId, EducationDTO updatedEducation);

    void deleteEducation(String educationId);
}