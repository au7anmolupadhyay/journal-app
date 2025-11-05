package net.au7.journalApp.service;

import net.au7.journalApp.entity.User;
import net.au7.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)  // ✅ Enables @Mock and @InjectMocks
public class UserDetailServiceImplTests {

    @InjectMocks
    private UserDetailServiceImpl userDetailService;  // Class under test

    @Mock
    private UserRepository userRepository;  // Mock dependency

    @Test
    public void userDetailServiceImplTest() {
        // Create entity using Lombok-generated constructor
        User userEntity = new User("Ram", "123456789");

        // Mock repository behavior
        when(userRepository.findByUsername(anyString())).thenReturn(userEntity);

        // Call the actual service method
        UserDetails user = userDetailService.loadUserByUsername("Ram");

        // Assertions to verify expected behavior
        assertNotNull(user);
        assertEquals("Ram", user.getUsername());
        verify(userRepository, times(1)).findByUsername("Ram");
    }
}
