package org.resumehub.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.resumehub.backend.dto.ReferenceDTO;
import org.resumehub.backend.exception.ResourceNotFoundException;
import org.resumehub.backend.service.ReferenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ReferenceControllerTest {

    private final String authorization = "eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE3MTU1NTA3OTEsImV4cCI6MTcxNTYzNzE5MSwiZW1haWwiOiJzZXRodXNlcmdlQHlhaG9vLmNvbSIsImF1dGhvcml0aWVzIjoiIn0.lGi6KXPSEmlrpSUaAEpWc6nbek8idH_JXUpMIDDmZ72QmGzVPqJXHgJW4hPlpt3Z";
    @Mock
    private ReferenceService referenceService;
    @InjectMocks
    private ReferenceController referenceController;

    @BeforeEach
    @Deprecated
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllReferences() {
        // Mock Data
        List<ReferenceDTO> referenceDTOList = new ArrayList<>();
        referenceDTOList.add(new ReferenceDTO(
                "663fa70feaf5de0442e70ea2",
                "663fb95b364adc66334cb83a",
                "Damian Naidoo",
                "Gumtree",
                "damian.naidoo@gumtree.co.za",
                "+27 81 451 4022"
        ));

        // Mock service method
        when(referenceService.getAllReferences()).thenReturn(referenceDTOList);

        // Call controller method
        ResponseEntity<List<ReferenceDTO>> response = referenceController.getAllReferences(authorization);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(referenceDTOList, response.getBody());
    }

    @Test
    public void testGetReferenceById() {
        // Mock data
        var referenceDto = new ReferenceDTO(
                "663fa70feaf5de0442e70ea2",
                "663fb95b364adc66334cb83a",
                "Damian Naidoo",
                "Gumtree",
                "damian.naidoo@gumtree.co.za",
                "+27 81 451 4022"
        );

        // Mocking referenceService.getReferenceById(referenceDto.getId()) to return ReferenceDTO
        when(referenceService.getReferenceById(referenceDto.getId())).thenReturn(referenceDto);

        // Call the Controller method
        ResponseEntity<ReferenceDTO> referenceDtoResponseEntity = referenceController.getReferenceById(authorization, referenceDto.getId());

        // Verify the response entity
        assertEquals(HttpStatus.OK, referenceDtoResponseEntity.getStatusCode());
        assertEquals(referenceDto, referenceDtoResponseEntity.getBody());
    }

    @Test
    public void testReference_ResourceNotFoundException() {
        // Mock data
        String referenceId = "663d89525a32f82254013cb9101";

        // Mocking referenceService.getReferenceById(referenceId) to throw a ResourceNotFoundException
        when(referenceService.getReferenceById(referenceId)).thenThrow(new ResourceNotFoundException("Reference Experience not found"));

        // Call the controller method and assert that ResourceNotFundException is thrown
        assertThrows(ResourceNotFoundException.class, () -> referenceController.getReferenceById(authorization, referenceId));

    }

    @Test
    public void testCreateNewReference() {
        // Mock data
        var referenceDto = new ReferenceDTO(
                "663fa70feaf5de0442e70ea2",
                "663fb95b364adc66334cb83a",
                "Damian Naidoo",
                "Gumtree",
                "damian.naidoo@gumtree.co.za",
                "+27 81 451 4022"
        );

        // Mock service method
        when(referenceService.createNewReference(any(ReferenceDTO.class))).thenReturn(referenceDto);

        // Call controller method
        ResponseEntity<ReferenceDTO> response = referenceController.createNewReference(authorization, referenceDto);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(referenceDto, response.getBody());
    }

    @Test
    public void testUpdateReference() {
        // Given
        String referenceId = "663d89525a32f82254013cb9";
        // Mock data
        var referenceDto = new ReferenceDTO(
                "663fa70feaf5de0442e70ea2",
                "663fb95b364adc66334cb83a",
                "Damian Naidoo",
                "Gumtree",
                "damian.naidoo@gumtree.co.za",
                "+27 81 451 4022"
        );


        // When
        when(referenceService.updateReference(referenceId, referenceDto)).thenReturn(new ReferenceDTO());

        // Then
        ResponseEntity<ReferenceDTO> response = referenceController.updateReference(authorization, referenceId, referenceDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateReference_ResourceNotFoundException() {
        // Given
        String referenceId = "663d89525a32f82254013cb910";
        var updatedReference = new ReferenceDTO();

        // When
        when(referenceService.updateReference(referenceId, updatedReference)).thenThrow(new ResourceNotFoundException("Resource not found"));

        // Then
        assertThrows(ResourceNotFoundException.class, () -> referenceController.updateReference(authorization, referenceId, updatedReference));
    }

    @Test
    public void testDeleteReference() {
        // Given
        String referenceId = "663d89525a32f82254013cb9";

        // When
        ResponseEntity<String> stringResponseEntity = referenceController.deleteReference(authorization, referenceId);

        // Then
        assertEquals(HttpStatus.OK, stringResponseEntity.getStatusCode());
        assertEquals("Reference deleted successful", stringResponseEntity.getBody());
        verify(referenceService, times(1)).deleteReference(referenceId);
    }
}
