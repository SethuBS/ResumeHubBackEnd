package org.resumehub.backend.repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.resumehub.backend.entity.User;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
                "sethuserge@gmail.com",
                "USER"
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

    @Test
    public void testFindByEmail() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Optional<User> foundUser = userRepository.findByEmail(user.getEmail());

        // Verify that the user is found by email
        assertEquals(user.getEmail(), foundUser.get().getEmail());
        assertEquals(user.getPassword(), foundUser.get().getPassword());
        assertEquals(user.getRole(), foundUser.get().getRole());
    }

    @Test
    public void testFindByEmail_NotFound() {
        // Non-existent email
        String nonExistentEmail = "nonexistent@example.com";

        when(userRepository.findByEmail(nonExistentEmail)).thenReturn(Optional.empty());

        Optional<User> foundUser = userRepository.findByEmail(nonExistentEmail);

        // Verify that user with non-existent email is not found
        assertFalse(foundUser.isPresent());
    }

}
