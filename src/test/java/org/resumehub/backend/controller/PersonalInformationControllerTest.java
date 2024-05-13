package org.resumehub.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.resumehub.backend.dto.PersonalInformationDTO;
import org.resumehub.backend.exception.ResourceAlreadyExistsException;
import org.resumehub.backend.exception.ResourceNotFoundException;
import org.resumehub.backend.service.PersonalInformationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class PersonalInformationControllerTest {

    private final String authorization = "eyJhbGciOiJIUzM4NCJ9.eyJpYXQiOjE3MTU1NTA3OTEsImV4cCI6MTcxNTYzNzE5MSwiZW1haWwiOiJzZXRodXNlcmdlQHlhaG9vLmNvbSIsImF1dGhvcml0aWVzIjoiIn0.lGi6KXPSEmlrpSUaAEpWc6nbek8idH_JXUpMIDDmZ72QmGzVPqJXHgJW4hPlpt3Z";
    @Mock
    private PersonalInformationService personalInformationService;
    @InjectMocks
    private PersonalInformationController personalInformationController;

    @BeforeEach
    @Deprecated
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllPersonalInformation() {
        // Mock data
        List<PersonalInformationDTO> personalInformationList = new ArrayList<>();
        personalInformationList.add(new PersonalInformationDTO(
                "1",
                "663fb95b364adc66334cb83a",
                "John Doe",
                "Engineer",
                "ABC Inc",
                "City1",
                "Province1",
                "Country1",
                "1234567890",
                "john.doe@example.com",
                "linkedin.com/johndoe"
        ));

        // Mock service method
        when(personalInformationService.getAllPersonalInformation()).thenReturn(personalInformationList);

        // Call controller method
        ResponseEntity<List<PersonalInformationDTO>> response = personalInformationController.getAllPersonalInformation(authorization);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personalInformationList, response.getBody());
    }

    @Test
    public void testAddPersonalInformation() {
        // Mock data
        var personalInformationToAdd = new PersonalInformationDTO(
                "1",
                "663fb95b364adc66334cb83a",
                "Jane Doe",
                "Manager",
                "XYZ Corp",
                "City2",
                "Province2",
                "Country2",
                "9876543210",
                "jane.doe@example.com",
                "linkedin.com/janedoe"
        );

        // Mock service method
        when(personalInformationService.savePersonalInformation(any(PersonalInformationDTO.class))).thenReturn(personalInformationToAdd);

        // Call controller method
        ResponseEntity<PersonalInformationDTO> response = personalInformationController.addPersonalInformation(authorization, personalInformationToAdd);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personalInformationToAdd, response.getBody());
    }

    @Test
    public void testSavePersonalInformation_ResourceAlreadyExists() {
        // Mock data
        PersonalInformationDTO existingPersonalInformation = new PersonalInformationDTO(
                "1",
                "663fb95b364adc66334cb83a",
                "John Doe",
                "Engineer",
                "ABC Inc",
                "City1",
                "Province1",
                "Country1",
                "1234567890",
                "john.doe@example.com",
                "linkedin.com/johndoe");

        // Mock the personalInformationService's savePersonalInformation method to throw a ResourceAlreadyExistsException
        when(personalInformationService.savePersonalInformation(any(PersonalInformationDTO.class))).thenThrow(new ResourceAlreadyExistsException("User with given email address: " + existingPersonalInformation.getEmail() + " already exists in the system"));

        // Verify that the exception is thrown
        assertThrows(ResourceAlreadyExistsException.class, () -> personalInformationController.addPersonalInformation(authorization, existingPersonalInformation));
    }

    @Test
    public void testGetPersonalInformationById() {
        // Mock data
        var personalInformation = new PersonalInformationDTO(
                "1",
                "663fb95b364adc66334cb83a",
                "John Doe",
                "Engineer",
                "ABC Inc",
                "City1",
                "Province1",
                "Country1",
                "1234567890",
                "john.doe@example.com",
                "linkedin.com/johndoe"
        );

        // Mocking personalInformationService.getPersonalInformationById(personalInformationId) to return PersonalInformationDTO
        when(personalInformationService.getPersonalInformationById(personalInformation.getId())).thenReturn(personalInformation);

        // Call the Controller method
        ResponseEntity<PersonalInformationDTO> personalInformationDtoResponseEntity = personalInformationController.getPersonalInformationById(authorization, personalInformation.getId());

        // Verify the response entity
        assertEquals(HttpStatus.OK, personalInformationDtoResponseEntity.getStatusCode());
        assertEquals(personalInformation, personalInformationDtoResponseEntity.getBody());
    }

    @Test
    public void testGetPersonalInformation_ResourceNotFoundException() {
        // Mock data
        String personalInformationId = "663d89525a32f82254013cb9101";

        // Mocking personalInformationService.getPersonalInformationById(personalInformationId) to throw a ResourceNotFoundException
        when(personalInformationService.getPersonalInformationById(personalInformationId)).thenThrow(new ResourceNotFoundException("User not found"));

        // Call the controller method and assert that ResourceNotFundException is thrown
        assertThrows(ResourceNotFoundException.class, () -> personalInformationController.getPersonalInformationById(authorization, personalInformationId));

    }

    @Test
    public void testUpdatePersonalInformation() {
        // Given
        String personalInformationId = "663d89525a32f82254013cb9";
        var updatedPersonalInformation = new PersonalInformationDTO(
                "1",
                "663fb95b364adc66334cb83a",
                "John Doe",
                "Engineer",
                "ABC Inc",
                "City1",
                "Province1",
                "Country1",
                "1234567890",
                "john.doe@example.com",
                "linkedin.com/johndoe"
        );


        // When
        when(personalInformationService.updatePersonalInformation(personalInformationId, updatedPersonalInformation)).thenReturn(new PersonalInformationDTO());

        // Then
        ResponseEntity<PersonalInformationDTO> response = personalInformationController.updatePersonalInformation(authorization, personalInformationId, updatedPersonalInformation);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdatePersonalInformation_ResourceNotFoundException() {
        // Given
        String personalInformationId = "663d89525a32f82254013cb910";
        PersonalInformationDTO updatedPersonalInformation = new PersonalInformationDTO();

        // When
        when(personalInformationService.updatePersonalInformation(personalInformationId, updatedPersonalInformation)).thenThrow(new ResourceNotFoundException("Resource not found"));

        // Then
        assertThrows(ResourceNotFoundException.class, () -> personalInformationController.updatePersonalInformation(authorization, personalInformationId, updatedPersonalInformation));
    }

    @Test
    public void testDeletePersonalInformation() {
        // Given
        String personalInformationId = "663d89525a32f82254013cb9";

        // When
        ResponseEntity<String> stringResponseEntity = personalInformationController.deletePersonalInformation(authorization, personalInformationId);

        // Then
        assertEquals(HttpStatus.OK, stringResponseEntity.getStatusCode());
        assertEquals("Personal Information deleted successful", stringResponseEntity.getBody());
        verify(personalInformationService, times(1)).deletePersonalInformation(personalInformationId);
    }

}