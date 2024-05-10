package org.resumehub.backend.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.resumehub.backend.entity.PersonalInformation;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonalInformationRepositoryTest {

    @Mock
    private PersonalInformationRepository personalInformationRepository;

    private PersonalInformation personalInformation;

    @Before
    public void setup() {
        personalInformation = new PersonalInformation(
                "1",
                "John Doe",
                "Engineer",
                "ABC Inc",
                "City1",
                "Province1",
                "Country1",
                "1234567890",
                "john.doe@example.com",
                "linkedin.com/johndoe");
        when(personalInformationRepository.findByEmail(personalInformation.getEmail())).thenReturn(personalInformation);
    }

    @Test
    public void testFindByEmail() {
        // When
        var result = personalInformationRepository.findByEmail(personalInformation.getEmail());

        // Then
        assertEquals(personalInformation, result);
    }

    @Test
    public void testFindByEmail_NotFound() {
        // Given
        when(personalInformationRepository.findByEmail(personalInformation.getEmail())).thenReturn(personalInformation);

        // When
        var result = personalInformationRepository.findByEmail(personalInformation.getEmail());

        // Then
        assertEquals(personalInformation, result);
    }
}
