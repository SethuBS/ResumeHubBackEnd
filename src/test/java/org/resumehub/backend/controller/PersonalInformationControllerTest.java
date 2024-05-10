package org.resumehub.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.resumehub.backend.dto.PersonalInformationDto;
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

    @Mock
    private PersonalInformationService personalInformationService;

    @InjectMocks
    private PersonalInformationController personalInformationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllPersonalInformation() {
        // Mock data
        List<PersonalInformationDto> personalInformationList = new ArrayList<>();
        personalInformationList.add(new PersonalInformationDto("1", "John Doe", "Engineer", "ABC Inc", "City1", "Province1", "Country1", "1234567890", "john.doe@example.com", "linkedin.com/johndoe"));

        // Mock service method
        when(personalInformationService.getAllPersonalInformation()).thenReturn(personalInformationList);

        // Call controller method
        ResponseEntity<List<PersonalInformationDto>> response = personalInformationController.getAllPersonalInformation();

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personalInformationList, response.getBody());
    }

    @Test
    public void testAddPersonalInformation() {
        // Mock data
        PersonalInformationDto personalInformationToAdd = new PersonalInformationDto("1", "Jane Doe", "Manager", "XYZ Corp", "City2", "Province2", "Country2", "9876543210", "jane.doe@example.com", "linkedin.com/janedoe");

        // Mock service method
        when(personalInformationService.savePersonalInformation(any(PersonalInformationDto.class))).thenReturn(personalInformationToAdd);

        // Call controller method
        ResponseEntity<PersonalInformationDto> response = personalInformationController.addPersonalInformation(personalInformationToAdd);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(personalInformationToAdd, response.getBody());
    }

    @Test
    public void testSavePersonalInformation_ResourceAlreadyExists() {
        // Mock data
        PersonalInformationDto existingPersonalInformation = new PersonalInformationDto("1", "John Doe", "Engineer", "ABC Inc", "City1", "Province1", "Country1", "1234567890", "john.doe@example.com", "linkedin.com/johndoe");

        // Mock the personalInformationService's savePersonalInformation method to throw a ResourceAlreadyExistsException
        when(personalInformationService.savePersonalInformation(any(PersonalInformationDto.class))).thenThrow(new ResourceAlreadyExistsException("User with given email address: " + existingPersonalInformation.getEmail() + " does not exist"));

        // Verify that the exception is thrown
        assertThrows(ResourceAlreadyExistsException.class, () -> personalInformationController.addPersonalInformation(existingPersonalInformation));
    }

    @Test
    public void testGetPersonalInformationById() {
        // Mock data
        var personalInformation = new PersonalInformationDto("1", "John Doe", "Engineer", "ABC Inc", "City1", "Province1", "Country1", "1234567890", "john.doe@example.com", "linkedin.com/johndoe");

        // Mocking personalInformationService.getPersonalInformationById(personalInformationId) to return PersonalInformationDto
        when(personalInformationService.getPersonalInformationById(personalInformation.getId())).thenReturn(personalInformation);

        // Call the Controller method
        ResponseEntity<PersonalInformationDto> personalInformationDtoResponseEntity = personalInformationController.getPersonalInformationById(personalInformation.getId());

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
        assertThrows(ResourceNotFoundException.class, () -> personalInformationController.getPersonalInformationById(personalInformationId));

    }

    @Test
    public void testUpdatePersonalInformation() {
        // Given
        String personalInformationId = "663d89525a32f82254013cb9";
        var updatedPersonalInformation = new PersonalInformationDto("1", "John Doe", "Engineer", "ABC Inc", "City1", "Province1", "Country1", "1234567890", "john.doe@example.com", "linkedin.com/johndoe");


        // When
        when(personalInformationService.updatePersonalInformation(personalInformationId, updatedPersonalInformation)).thenReturn(new PersonalInformationDto());

        // Then
        ResponseEntity<PersonalInformationDto> response = personalInformationController.updatePersonalInformation(personalInformationId, updatedPersonalInformation);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdatePersonalInformation_ResourceNotFoundException() {
        // Given
        String personalInformationId = "663d89525a32f82254013cb910";
        PersonalInformationDto updatedPersonalInformation = new PersonalInformationDto();

        // When
        when(personalInformationService.updatePersonalInformation(personalInformationId, updatedPersonalInformation)).thenThrow(new ResourceNotFoundException("Resource not found"));

        // Then
        assertThrows(ResourceNotFoundException.class, () -> personalInformationController.updatePersonalInformation(personalInformationId, updatedPersonalInformation));
    }

    @Test
    public void testDeletePersonalInformation() {
        // Given
        String personalInformationId = "663d89525a32f82254013cb9";

        // When
        ResponseEntity<String> stringResponseEntity = personalInformationController.deletePersonalInformation(personalInformationId);

        // Then
        assertEquals(HttpStatus.OK, stringResponseEntity.getStatusCode());
        assertEquals("Personal Information deleted successful", stringResponseEntity.getBody());
        verify(personalInformationService, times(1)).deletePersonalInformation(personalInformationId);
    }

}