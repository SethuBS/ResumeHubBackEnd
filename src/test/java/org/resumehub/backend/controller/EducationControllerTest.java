package org.resumehub.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.resumehub.backend.dto.EducationDTO;
import org.resumehub.backend.exception.ResourceNotFoundException;
import org.resumehub.backend.service.EducationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EducationControllerTest {

    @Mock
    private EducationService educationService;

    @InjectMocks
    private EducationController educationController;

    private final String authorization = "eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE3MTU1NTA3OTEsImV4cCI6MTcxNTYzNzE5MSwiZW1haWwiOiJzZXRodXNlcmdlQHlhaG9vLmNvbSIsImF1dGhvcml0aWVzIjoiIn0.lGi6KXPSEmlrpSUaAEpWc6nbek8idH_JXUpMIDDmZ72QmGzVPqJXHgJW4hPlpt3Z";

    @BeforeEach
    @Deprecated
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllEducation() {
        // Mock data
        List<EducationDTO> educationDTOList = new ArrayList<>();
        educationDTOList.add(new EducationDTO(
                "663d89525a32f82254013cb9",
                "663fb95b364adc66334cb83a",
                "Damelin",
                "Higher National Diploma",
                "Computer Software Engineering",
                "2011",
                "2014"
        ));

        // Mock service method
        when(educationService.getAllEducation()).thenReturn(educationDTOList);

        // Call controller method
        ResponseEntity<List<EducationDTO>> response = educationController.getAllEducation(authorization);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(educationDTOList, response.getBody());

    }

    @Test
    public void testGetEducationById() {
        // Mock data
        var existingEducation = new EducationDTO(
                "663d89525a32f82254013cb9",
                "663fb95b364adc66334cb83a",
                "Damelin",
                "Higher National Diploma",
                "Computer Software Engineering",
                "2011",
                "2014"
        );

        // Mocking educationService.getEducationById(existingEducation.getId()) to return EducationDto
        when(educationService.getEducationById(existingEducation.getId())).thenReturn(existingEducation);

        // Call the Controller method
        ResponseEntity<EducationDTO> educationDtoResponseEntity = educationController.getEducationById(authorization, existingEducation.getId());

        // Verify the response entity
        assertEquals(HttpStatus.OK, educationDtoResponseEntity.getStatusCode());
        assertEquals(existingEducation, educationDtoResponseEntity.getBody());
    }

    @Test
    public void testEducation_ResourceNotFoundException() {
        // Mock data
        String educationId = "663d89525a32f82254013cb9101";

        // Mocking educationService.getEducationById(educationId) to throw a ResourceNotFoundException
        when(educationService.getEducationById(educationId)).thenThrow(new ResourceNotFoundException("Education not found"));

        // Call the controller method and assert that ResourceNotFundException is thrown
        assertThrows(ResourceNotFoundException.class, () -> educationController.getEducationById(authorization, educationId));

    }

    @Test
    public void testCreateEducation() {
        // Mock data
        var newEducation = new EducationDTO(
                "663d89525a32f82254013cb9",
                "663fb95b364adc66334cb83a",
                "Damelin",
                "Higher National Diploma",
                "Computer Software Engineering",
                "2011",
                "2014"
        );

        // Mock service method
        when(educationService.createEducation(any(EducationDTO.class))).thenReturn(newEducation);

        // Call controller method
        ResponseEntity<EducationDTO> response = educationController.createEducation(authorization, newEducation);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newEducation, response.getBody());
    }

    @Test
    public void testUpdateEducation() {
        // Given
        String educationId = "663d89525a32f82254013cb9";
        var updatedEducation = new EducationDTO(
                "663d89525a32f82254013cb9",
                "663fb95b364adc66334cb83a",
                "Damelin",
                "Higher National Diploma",
                "Computer Software Engineering",
                "2011",
                "2014"
        );


        // When
        when(educationService.updateEducation(educationId, updatedEducation)).thenReturn(new EducationDTO());

        // Then
        ResponseEntity<EducationDTO> response = educationController.updateEducation(authorization, educationId, updatedEducation);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateEducation_ResourceNotFoundException() {
        // Given
        String educationId = "663d89525a32f82254013cb910";
        var updatedEducation = new EducationDTO();

        // When
        when(educationService.updateEducation(educationId, updatedEducation)).thenThrow(new ResourceNotFoundException("Resource not found"));

        // Then
        assertThrows(ResourceNotFoundException.class, () -> educationController.updateEducation(authorization, educationId, updatedEducation));
    }

    @Test
    public void testDeleteEducation() {
        // Given
        String educationId = "663d89525a32f82254013cb9";

        // When
        ResponseEntity<String> stringResponseEntity = educationController.deleteEducation(authorization, educationId);

        // Then
        assertEquals(HttpStatus.OK, stringResponseEntity.getStatusCode());
        assertEquals("Education deleted successful", stringResponseEntity.getBody());
        verify(educationService, times(1)).deleteEducation(educationId);
    }
}
