package org.resumehub.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.resumehub.backend.dto.WorkExperienceDTO;
import org.resumehub.backend.exception.ResourceNotFoundException;
import org.resumehub.backend.service.WorkExperienceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class WorkExperienceControllerTest {

    final List<String> skills = Arrays.asList(
            "Java",
            "Microservices",
            "Spring Framework",
            "MongoDB",
            "Jenkins",
            "Github"

    );
    final List<String> responsibilities = Arrays.asList(
            "Executed full software development life cycle (SDLC).",
            "Developed flowcharts, layouts, and documentation to identify requirements and solutions.",
            "Wrote well-designed, testable code.",
            "Produced specifications and determined operational feasibility.",
            "Integrated software components into fully functional software systems.",
            "Developed software verification plans and quality assurance procedures.",
            "Documented and maintained software functionality.",
            "Troubleshooted, debugged, and upgraded existing systems.",
            "Deployed programs and evaluated user feedback.",
            "Complied with project plans and industry standards.",
            "Ensured software was updated with latest features."
    );
    private final String authorization = "eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE3MTU1NTA3OTEsImV4cCI6MTcxNTYzNzE5MSwiZW1haWwiOiJzZXRodXNlcmdlQHlhaG9vLmNvbSIsImF1dGhvcml0aWVzIjoiIn0.lGi6KXPSEmlrpSUaAEpWc6nbek8idH_JXUpMIDDmZ72QmGzVPqJXHgJW4hPlpt3Z";
    @Mock
    private WorkExperienceService workExperienceService;
    @InjectMocks
    private WorkExperienceController workExperienceController;

    @BeforeEach
    @Deprecated
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllWorkExperience() {
        // Mock data
        List<WorkExperienceDTO> workExperienceDTOList = new ArrayList<>();
        workExperienceDTOList.add(new WorkExperienceDTO(
                "663d89525a32f82254013cb9",
                "663fb95b364adc66334cb83a",
                "Gumtree South Africa",
                "Intermediate Backend Engineer",
                "July 2023",
                "October 2023",
                "Cape Town",
                "Western Cape",
                "South Africa",
                responsibilities,
                skills
        ));

        // Mock service method
        when(workExperienceService.getAllWorkExperience()).thenReturn(workExperienceDTOList);

        // Call controller method
        ResponseEntity<List<WorkExperienceDTO>> response = workExperienceController.getAllWorkExperience(authorization);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(workExperienceDTOList, response.getBody());

    }

    @Test
    public void testGetWorkExperienceById() {
        // Mock data
        var workExperience = new WorkExperienceDTO(
                "663d89525a32f82254013cb9",
                "663fb95b364adc66334cb83a",
                "Gumtree South Africa",
                "Intermediate Backend Engineer",
                "July 2023",
                "October 2023",
                "Cape Town",
                "Western Cape",
                "South Africa",
                responsibilities,
                skills
        );

        // Mocking workExperienceService.getWorkExperienceById(workExperienceId) to return WorkExperienceDTO
        when(workExperienceService.getWorkExperienceById(workExperience.getId())).thenReturn(workExperience);

        // Call the Controller method
        ResponseEntity<WorkExperienceDTO> workExperienceDtoResponseEntity = workExperienceController.getWorkExperienceById(authorization, workExperience.getId());

        // Verify the response entity
        assertEquals(HttpStatus.OK, workExperienceDtoResponseEntity.getStatusCode());
        assertEquals(workExperience, workExperienceDtoResponseEntity.getBody());
    }

    @Test
    public void testWorkExperience_ResourceNotFoundException() {
        // Mock data
        String workExperienceId = "663d89525a32f82254013cb9101";

        // Mocking workExperienceService.getWorkExperienceById(workExperienceId) to throw a ResourceNotFoundException
        when(workExperienceService.getWorkExperienceById(workExperienceId)).thenThrow(new ResourceNotFoundException("Work Experience not found"));

        // Call the controller method and assert that ResourceNotFundException is thrown
        assertThrows(ResourceNotFoundException.class, () -> workExperienceController.getWorkExperienceById(authorization, workExperienceId));

    }

    @Test
    public void testSaveWorkExperience() {
        // Mock data
        var workExperience = new WorkExperienceDTO(
                "663d89525a32f82254013cb9",
                "663fb95b364adc66334cb83a",
                "Gumtree South Africa",
                "Intermediate Backend Engineer",
                "July 2023",
                "October 2023",
                "Cape Town",
                "Western Cape",
                "South Africa",
                responsibilities,
                skills
        );

        // Mock service method
        when(workExperienceService.saveWorkExperience(any(WorkExperienceDTO.class))).thenReturn(workExperience);

        // Call controller method
        ResponseEntity<WorkExperienceDTO> response = workExperienceController.saveWorkExperience(authorization, workExperience);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(workExperience, response.getBody());
    }

    @Test
    public void testUpdateWorkExperience() {
        // Given
        String workExperienceId = "663d89525a32f82254013cb9";
        var updatedWorkExperience = new WorkExperienceDTO(
                "663d89525a32f82254013cb9",
                "663fb95b364adc66334cb83a",
                "Gumtree South Africa",
                "Intermediate Backend Engineer",
                "July 2023",
                "October 2023",
                "Cape Town",
                "Western Cape",
                "South Africa",
                responsibilities,
                skills
        );


        // When
        when(workExperienceService.updateWorkExperience(workExperienceId, updatedWorkExperience)).thenReturn(new WorkExperienceDTO());

        // Then
        ResponseEntity<WorkExperienceDTO> response = workExperienceController.updateWorkExperience(authorization, workExperienceId, updatedWorkExperience);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateWorkExperience_ResourceNotFoundException() {
        // Given
        String workExperienceId = "663d89525a32f82254013cb910";
        var updatedWorkExperience = new WorkExperienceDTO();

        // When
        when(workExperienceService.updateWorkExperience(workExperienceId, updatedWorkExperience)).thenThrow(new ResourceNotFoundException("Resource not found"));

        // Then
        assertThrows(ResourceNotFoundException.class, () -> workExperienceController.updateWorkExperience(authorization, workExperienceId, updatedWorkExperience));
    }

    @Test
    public void testDeletePersonalInformation() {
        // Given
        String workExperienceId = "663d89525a32f82254013cb9";

        // When
        ResponseEntity<String> stringResponseEntity = workExperienceController.deleteWorkExperience(authorization, workExperienceId);

        // Then
        assertEquals(HttpStatus.OK, stringResponseEntity.getStatusCode());
        assertEquals("Work Experience deleted successful", stringResponseEntity.getBody());
        verify(workExperienceService, times(1)).deleteWorkExperience(workExperienceId);
    }
}
