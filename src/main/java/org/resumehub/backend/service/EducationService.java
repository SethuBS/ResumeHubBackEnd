package org.resumehub.backend.service;

import org.resumehub.backend.dto.EducationDto;

import java.util.List;

public interface EducationService {
    List<EducationDto> getAllEducation();

    EducationDto getEducationById(String educationId);

    EducationDto createEducation(EducationDto newEducation);

    EducationDto updateEducation(String educationId, EducationDto updatedEducation);

    void deleteEducation(String educationId);
}