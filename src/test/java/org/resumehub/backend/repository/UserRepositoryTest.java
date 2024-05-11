package org.resumehub.backend.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.resumehub.backend.entity.User;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private User user;

    @Before
    public void setup() {
        // Mock Data
        user = new User(
                "663fb95b364adc66334cb83a",
                "sethuserge@gmail.com",
                "sethuserge@gmail.com"
        );
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(user);
    }

    @Test
    public void testFindUserByEmail() {
        // When
        var result = userRepository.findUserByEmail(user.getEmail());

        // Then
        assertEquals(user, result);
    }

    @Test
    public void testFindUserByEmail_NotFound() {
        // Given
        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(user);

        // When
        var result = userRepository.findUserByEmail(user.getEmail());

        // Then
        assertEquals(user, result);
    }
}
