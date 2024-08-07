package bg.softuni.parking.service;

import bg.softuni.parking.Constants;
import bg.softuni.parking.exception.UsernameNotFoundException;
import bg.softuni.parking.model.entities.Role;
import bg.softuni.parking.model.entities.User;
import bg.softuni.parking.model.enums.UserRoleEnum;
import bg.softuni.parking.model.user.ParkingUserDetails;
import bg.softuni.parking.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParkingUserDetailsServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private ParkingUserDetailsService parkingUserDetailsService;
    
    @Test
    void testLoadUserByUsername_UserExists() {
        // Arrange
        Role userRole = new Role();
        userRole.setName(UserRoleEnum.USER);
        
        User user = new User();
        user.setUsername(Constants.USERNAME);
        user.setPassword(Constants.PASSWORD);
        user.setFirstName(Constants.FIRST_NAME);
        user.setLastName(Constants.LAST_NAME);
        user.setPhone(Constants.PHONE);
        user.setEmail(Constants.EMAIL);
        user.setUuid(Constants.UUID);
        user.setRoles(Set.of(userRole));
        
        when(userRepository.findByUsername(Constants.USERNAME)).thenReturn(Optional.of(user));
        
        // Act
        UserDetails userDetails = parkingUserDetailsService.loadUserByUsername(Constants.USERNAME);
        
        // Assert
        assertEquals(Constants.USERNAME, userDetails.getUsername());
        assertEquals(Constants.PASSWORD, userDetails.getPassword());
        assertEquals(1, userDetails.getAuthorities().size());
        assertEquals("ROLE_USER", userDetails.getAuthorities().iterator().next().getAuthority());
        
        ParkingUserDetails parkingUserDetails = (ParkingUserDetails) userDetails;
        assertEquals(Constants.FIRST_NAME, parkingUserDetails.getFirstName());
        assertEquals(Constants.LAST_NAME, parkingUserDetails.getLastName());
        assertEquals(Constants.PHONE, parkingUserDetails.getPhone());
        assertEquals(Constants.EMAIL, parkingUserDetails.getEmail());
        assertEquals(Constants.UUID, parkingUserDetails.getUuid());
    }
    
    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Arrange
        when(userRepository.findByUsername(Constants.USERNAME)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            parkingUserDetailsService.loadUserByUsername(Constants.USERNAME);
        });
    }
}
