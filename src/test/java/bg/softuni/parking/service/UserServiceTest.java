package bg.softuni.parking.service;

import bg.softuni.parking.exception.InvalidUserIdException;
import bg.softuni.parking.exception.InvalidUserTypeException;
import bg.softuni.parking.exception.RoleNotFoundException;
import bg.softuni.parking.exception.UsernameNotFoundException;
import bg.softuni.parking.model.dto.*;
import bg.softuni.parking.model.dto.vehicle.VehicleView;
import bg.softuni.parking.model.entities.ParkingSpot;
import bg.softuni.parking.model.entities.Reservation;
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
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static bg.softuni.parking.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  
  @Mock
  private UserRepository userRepository;
  
  @Mock
  private ModelMapper modelMapper;
  
  @Mock
  private PasswordEncoder passwordEncoder;
  
  @Mock
  private RoleService roleService;
  
  @Mock
  private ParkingUserDetailsService parkingUserDetailsService;
  
  @Mock
  private VehicleService vehicleService;
  
  @InjectMocks
  private UserService userService;
  
  @Test
  void testFindByUsername_Success() {
    // Arrange
    User user = new User();
    user.setUsername(USERNAME);
    
    when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
    
    // Act
    Optional<User> result = userService.findByUsername(USERNAME);
    
    // Assert
    assertTrue(result.isPresent());
    assertEquals(USERNAME, result.get().getUsername());
  }
  
  @Test
  void testFindByUsername_NotFound() {
    // Arrange
    when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
    
    // Act
    Optional<User> result = userService.findByUsername(USERNAME);
    
    // Assert
    assertTrue(result.isEmpty());
  }
  @Test
  void testFindAll_Success() {
    // Arrange
    User user1 = new User();
    user1.setUsername(USERNAME);
    
    User user2 = new User();
    user2.setUsername("anotherUser");
    
    when(userRepository.findAll()).thenReturn(List.of(user1, user2));
    
    // Act
    List<User> result = userService.findAll();
    
    // Assert
    assertEquals(2, result.size());
    assertEquals(USERNAME, result.get(0).getUsername());
    assertEquals("anotherUser", result.get(1).getUsername());
  }
  
  @Test
  void testFindByEmail_Success() {
    // Arrange
    User user = new User();
    user.setEmail(EMAIL);
    
    when(userRepository.findByEmail(EMAIL)).thenReturn(user);
    
    // Act
    User result = userService.findByEmail(EMAIL);
    
    // Assert
    assertEquals(EMAIL, result.getEmail());
  }
  
  @Test
  void testUpdateUser_Success() {
    // Arrange
    User user = new User();
    user.setUsername(USERNAME);
    
    UserUpdateDto userUpdateDto = new UserUpdateDto();
    userUpdateDto.setUsername("newUsername");
    userUpdateDto.setEmail(EMAIL);
    userUpdateDto.setPhone(PHONE);
    userUpdateDto.setFirstName(FIRST_NAME);
    userUpdateDto.setLastName(LAST_NAME);
    
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getName()).thenReturn(USERNAME);
    SecurityContextHolder.setContext(securityContext);
    
    when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
    
    ParkingUserDetails userDetails = mock(ParkingUserDetails.class);
    when(parkingUserDetailsService.loadUserByUsername("newUsername")).thenReturn(userDetails);
    
    // Act
    userService.updateUser(userUpdateDto);
    
    // Assert
    assertEquals("newUsername", user.getUsername());
    assertEquals(EMAIL, user.getEmail());
    assertEquals(PHONE, user.getPhone());
    assertEquals(FIRST_NAME, user.getFirstName());
    assertEquals(LAST_NAME, user.getLastName());
    verify(userRepository).saveAndFlush(user);
    verify(parkingUserDetailsService).loadUserByUsername("newUsername");
  }
  
  @Test
  void testGetUserProfile_Success() {
    // Arrange
    User user = new User();
    user.setUsername(USERNAME);
    user.setEmail(EMAIL);
    user.setPhone(PHONE);
    user.setFirstName(FIRST_NAME);
    user.setLastName(LAST_NAME);
    
    UserProfileDto userProfileDto = new UserProfileDto();
    userProfileDto.setUsername(USERNAME);
    userProfileDto.setEmail(EMAIL);
    userProfileDto.setPhone(PHONE);
    userProfileDto.setFirstName(FIRST_NAME);
    userProfileDto.setLastName(LAST_NAME);
    
    when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
    when(modelMapper.map(user, UserProfileDto.class)).thenReturn(userProfileDto);
    
    // Act
    UserProfileDto result = userService.getUserProfile(USERNAME);
    
    // Assert
    assertEquals(USERNAME, result.getUsername());
    assertEquals(EMAIL, result.getEmail());
    assertEquals(PHONE, result.getPhone());
    assertEquals(FIRST_NAME, result.getFirstName());
    assertEquals(LAST_NAME, result.getLastName());
    
    verify(userRepository).findByUsername(USERNAME);
    verify(modelMapper).map(user, UserProfileDto.class);
  }
  
  @Test
  void testUpdateUserProfile_Success() {
    // Arrange
    User user = new User();
    user.setUsername(USERNAME);
    
    UserProfileDto userProfileDto = new UserProfileDto();
    userProfileDto.setUsername(USERNAME);
    userProfileDto.setEmail(EMAIL);
    userProfileDto.setPhone(PHONE);
    userProfileDto.setFirstName(FIRST_NAME);
    userProfileDto.setLastName(LAST_NAME);
    
    when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
    
    // Act
    userService.updateUserProfile(userProfileDto);
    
    // Assert
    assertEquals(EMAIL, user.getEmail());
    assertEquals(PHONE, user.getPhone());
    assertEquals(FIRST_NAME, user.getFirstName());
    assertEquals(LAST_NAME, user.getLastName());
    
    verify(userRepository).findByUsername(USERNAME);
    verify(userRepository).save(user);
  }
  
  @Test
  void testChangePassword_Success() {
    // Arrange
    User user = new User();
    user.setUsername(USERNAME);
    user.setPassword(PASSWORD);
    
    ChangePasswordDto changePasswordDto = new ChangePasswordDto();
    changePasswordDto.setNewPassword(NEW_PASSWORD);
    
    when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
    when(passwordEncoder.encode(NEW_PASSWORD)).thenReturn("encodedNewPassword");
    
    // Act
    userService.changePassword(USERNAME, changePasswordDto);
    
    // Assert
    assertEquals("encodedNewPassword", user.getPassword());
    verify(userRepository).findByUsername(USERNAME);
    verify(userRepository).save(user);
  }
  
  @Test
  void testGetCurrentUser_Success() {
    // Arrange
    ParkingUserDetails userDetails = new ParkingUserDetails(
        USERNAME, PASSWORD, Collections.<GrantedAuthority>emptyList(),
        FIRST_NAME, LAST_NAME, PHONE, EMAIL, UUID
    );
    
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn(userDetails);
    SecurityContextHolder.setContext(securityContext);
    
    // Act
    ParkingUserDetails result = userService.getCurrentUser();
    
    // Assert
    assertEquals(USERNAME, result.getUsername());
    assertEquals(PASSWORD, result.getPassword());
    assertEquals(FIRST_NAME, result.getFirstName());
    assertEquals(LAST_NAME, result.getLastName());
    assertEquals(PHONE, result.getPhone());
    assertEquals(EMAIL, result.getEmail());
    assertEquals(UUID, result.getUuid());
    
    verify(securityContext).getAuthentication();
    verify(authentication).getPrincipal();
  }
  
  @Test
  void testGetCurrentUser_InvalidUserType() {
    // Arrange
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getPrincipal()).thenReturn("invalidUserType");
    SecurityContextHolder.setContext(securityContext);
    
    // Act & Assert
    assertThrows(InvalidUserTypeException.class, () -> userService.getCurrentUser());
    
    verify(securityContext).getAuthentication();
    verify(authentication).getPrincipal();
  }
  @Test
  void testGetAllUsersWithReservations_Success() {
    // Arrange
    User user = new User();
    user.setUsername(USERNAME);
    
    ParkingSpot parkingSpot = new ParkingSpot();
    parkingSpot.setLocation(PARKING_SPOT_LOCATION);
    
    Reservation reservation = new Reservation();
    reservation.setStartTime(TEST_DATE_TIME_1);
    reservation.setEndTime(TEST_DATE_TIME_2);
    reservation.setVehicleId(TEST_ID_1);
    reservation.setParkingSpot(parkingSpot);
    
    user.setReservations(Set.of(reservation));
    
    when(userRepository.findAll()).thenReturn(List.of(user));
    when(vehicleService.getVehicleById(TEST_ID_1)).thenReturn(new VehicleView(TEST_ID_1, VEHICLE_LICENSE_PLATE));
    
    // Act
    List<UserDto> result = userService.getAllUsersWithReservations();
    
    // Assert
    assertEquals(1, result.size());
    assertEquals(USERNAME, result.get(0).getUsername());
    assertEquals(1, result.get(0).getReservations().size());
    
    ReservationDto reservationDto = result.get(0).getReservations().get(0);
    assertEquals(TEST_DATE_TIME_1, reservationDto.getStartTime());
    assertEquals(TEST_DATE_TIME_2, reservationDto.getEndTime());
    assertEquals(VEHICLE_LICENSE_PLATE, reservationDto.getVehicleLicensePlate());
    assertEquals(PARKING_SPOT_LOCATION, reservationDto.getParkingSpotLocation());
    
    verify(userRepository).findAll();
    verify(vehicleService).getVehicleById(TEST_ID_1);
  }
  
  @Test
  void testUsernameExists_Success() {
    // Arrange
    when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(new User()));
    
    // Act
    boolean result = userService.usernameExists(USERNAME);
    
    // Assert
    assertTrue(result);
    verify(userRepository).findByUsername(USERNAME);
  }
  
  @Test
  void testEmailExists_Success() {
    // Arrange
    when(userRepository.existsByEmail(EMAIL)).thenReturn(true);
    
    // Act
    boolean result = userService.emailExists(EMAIL);
    
    // Assert
    assertTrue(result);
    verify(userRepository).existsByEmail(EMAIL);
  }
  @Test
  void testCheckUniqueUsername_NotUnique() {
    // Arrange
    when(userRepository.existsByUsernameAndNotCurrentUsername(USERNAME, ANOTHER_USERNAME)).thenReturn(true);
    
    // Act
    boolean result = userService.checkUniqueUsername(USERNAME, ANOTHER_USERNAME);
    
    // Assert
    assertTrue(result);
    verify(userRepository).existsByUsernameAndNotCurrentUsername(USERNAME, ANOTHER_USERNAME);
  }
  
  @Test
  void testCheckUniqueUsername_Unique() {
    // Arrange
    when(userRepository.existsByUsernameAndNotCurrentUsername(USERNAME, ANOTHER_USERNAME)).thenReturn(false);
    
    // Act
    boolean result = userService.checkUniqueUsername(USERNAME, ANOTHER_USERNAME);
    
    // Assert
    assertFalse(result);
    verify(userRepository).existsByUsernameAndNotCurrentUsername(USERNAME, ANOTHER_USERNAME);
  }
  
  @Test
  void testCheckUniqueEmail_NotUnique() {
    // Arrange
    when(userRepository.existsByEmailAndNotCurrentEmail(EMAIL, ANOTHER_EMAIL)).thenReturn(true);
    
    // Act
    boolean result = userService.checkUniqueEmail(EMAIL, ANOTHER_EMAIL);
    
    // Assert
    assertTrue(result);
    verify(userRepository).existsByEmailAndNotCurrentEmail(EMAIL, ANOTHER_EMAIL);
  }
  
  @Test
  void testCheckUniqueEmail_Unique() {
    // Arrange
    when(userRepository.existsByEmailAndNotCurrentEmail(EMAIL, ANOTHER_EMAIL)).thenReturn(false);
    
    // Act
    boolean result = userService.checkUniqueEmail(EMAIL, ANOTHER_EMAIL);
    
    // Assert
    assertFalse(result);
    verify(userRepository).existsByEmailAndNotCurrentEmail(EMAIL, ANOTHER_EMAIL);
  }
  
  @Test
  void testIsPasswordCorrect_CorrectPassword() {
    // Arrange
    when(passwordEncoder.matches(PASSWORD, ENCODED_PASSWORD)).thenReturn(true);
    
    // Act
    boolean result = userService.isPasswordCorrect(ENCODED_PASSWORD, PASSWORD);
    
    // Assert
    assertTrue(result);
    verify(passwordEncoder).matches(PASSWORD, ENCODED_PASSWORD);
  }
  
  @Test
  void testIsPasswordCorrect_IncorrectPassword() {
    // Arrange
    when(passwordEncoder.matches(PASSWORD,ENCODED_PASSWORD )).thenReturn(false);
    
    // Act
    boolean result = userService.isPasswordCorrect(ENCODED_PASSWORD, PASSWORD);
    
    // Assert
    assertFalse(result);
    verify(passwordEncoder).matches(PASSWORD, ENCODED_PASSWORD);
  }
  
  @Test
  void testPasswordsAreSame_SamePasswords() {
    // Arrange
    when(passwordEncoder.matches(PASSWORD, PASSWORD)).thenReturn(true);
    
    // Act
    boolean result = userService.passwordsAreSame(PASSWORD, PASSWORD);
    
    // Assert
    assertTrue(result);
    verify(passwordEncoder).matches(PASSWORD, PASSWORD);
  }
  
  @Test
  void testPasswordsAreSame_DifferentPasswords() {
    // Arrange
    String newPassword = NEW_PASSWORD;
    when(passwordEncoder.matches(newPassword, PASSWORD)).thenReturn(false);
    
    // Act
    boolean result = userService.passwordsAreSame(PASSWORD, newPassword);
    
    // Assert
    assertFalse(result);
    verify(passwordEncoder).matches(newPassword, PASSWORD);
  }
  
  @Test
  void testGetByUsername_UserExists() {
    // Arrange
    User user = new User();
    user.setUsername(USERNAME);
    
    when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));
    
    // Act
    User result = userService.getByUsername(USERNAME);
    
    // Assert
    assertEquals(user, result);
    verify(userRepository).findByUsername(USERNAME);
  }
  
  @Test
  void testGetByUsername_UserNotFound() {
    // Arrange
    when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
    
    // Act & Assert
    assertThrows(UsernameNotFoundException.class, () -> userService.getByUsername(USERNAME));
    verify(userRepository).findByUsername(USERNAME);
  }
  @Test
  void testFindByUuid_UserExists() {
    // Arrange
    User user = new User();
    user.setUuid(UUID);
    
    when(userRepository.findByUuid(UUID)).thenReturn(user);
    
    // Act
    User result = userService.findByUuid(UUID);
    
    // Assert
    assertEquals(user, result);
    verify(userRepository).findByUuid(UUID);
  }
  @Test
  void testGetAllUsersWithRoles() {
    // Arrange
    Role userRole = new Role();
    userRole.setName(UserRoleEnum.USER);
    
    Role adminRole = new Role();
    adminRole.setName(UserRoleEnum.ADMIN);
    
    User user1 = new User();
    user1.setId(TEST_ID_1);
    user1.setUsername(USERNAME);
    user1.setEmail(EMAIL);
    user1.setRoles(Set.of(userRole));
    
    User user2 = new User();
    user2.setId(TEST_ID_2);
    user2.setUsername("anotherUser");
    user2.setEmail("another.email@example.com");
    user2.setRoles(Set.of(adminRole));
    
    when(userRepository.findAll()).thenReturn(List.of(user1, user2));
    
    // Act
    List<UserWithRolesDto> result = userService.getAllUsersWithRoles();
    
    // Assert
    assertEquals(2, result.size());
    assertEquals(USERNAME, result.get(0).getUsername());
    assertEquals(1, result.get(0).getRoles().size());
    assertEquals(UserRoleEnum.USER.name(), result.get(0).getRoles().iterator().next());
    assertEquals("anotherUser", result.get(1).getUsername());
    assertEquals(1, result.get(1).getRoles().size());
    assertEquals(UserRoleEnum.ADMIN.name(), result.get(1).getRoles().iterator().next());
    
    verify(userRepository).findAll();
  }
  @Test
  void testAddAdminRole_UserExists_RoleExists() {
    // Arrange
    User user = new User();
    user.setId(TEST_ID_1);
    
    Role adminRole = new Role();
    adminRole.setName(UserRoleEnum.ADMIN);
    
    when(userRepository.findById(TEST_ID_1)).thenReturn(Optional.of(user));
    when(roleService.findRoleByName(UserRoleEnum.ADMIN)).thenReturn(Optional.of(adminRole));
    
    // Act
    userService.addAdminRole(TEST_ID_1);
    
    // Assert
    assertEquals(1, user.getRoles().size());
    assertEquals(adminRole, user.getRoles().iterator().next());
    verify(userRepository).findById(TEST_ID_1);
    verify(roleService).findRoleByName(UserRoleEnum.ADMIN);
    verify(userRepository).save(user);
  }
  
  @Test
  void testAddAdminRole_UserNotFound() {
    // Arrange
    when(userRepository.findById(TEST_ID_1)).thenReturn(Optional.empty());
    
    // Act & Assert
    assertThrows(InvalidUserIdException.class, () -> userService.addAdminRole(TEST_ID_1));
    verify(userRepository).findById(TEST_ID_1);
    verify(roleService, never()).findRoleByName(any());
    verify(userRepository, never()).save(any());
  }
  
  @Test
  void testAddAdminRole_RoleNotFound() {
    // Arrange
    User user = new User();
    user.setId(TEST_ID_1);
    
    when(userRepository.findById(TEST_ID_1)).thenReturn(Optional.of(user));
    when(roleService.findRoleByName(UserRoleEnum.ADMIN)).thenReturn(Optional.empty());
    
    // Act & Assert
    assertThrows(RoleNotFoundException.class, () -> userService.addAdminRole(TEST_ID_1));
    verify(userRepository).findById(TEST_ID_1);
    verify(roleService).findRoleByName(UserRoleEnum.ADMIN);
    verify(userRepository, never()).save(any());
  }
  @Test
  void testRemoveAdminRole_UserExists_RoleExists() {
    // Arrange
    Role adminRole = new Role();
    adminRole.setName(UserRoleEnum.ADMIN);
    
    User user = new User();
    user.setId(TEST_ID_1);
    user.setRoles(new HashSet<>(Set.of(adminRole)));
    
    when(userRepository.findById(TEST_ID_1)).thenReturn(Optional.of(user));
    when(roleService.findRoleByName(UserRoleEnum.ADMIN)).thenReturn(Optional.of(adminRole));
    
    // Act
    userService.removeAdminRole(TEST_ID_1);
    
    // Assert
    assertFalse(user.getRoles().contains(adminRole));
    verify(userRepository).findById(TEST_ID_1);
    verify(roleService).findRoleByName(UserRoleEnum.ADMIN);
    verify(userRepository).save(user);
  }
  
  @Test
  void testRemoveAdminRole_UserNotFound() {
    // Arrange
    when(userRepository.findById(TEST_ID_1)).thenReturn(Optional.empty());
    
    // Act & Assert
    assertThrows(InvalidUserIdException.class, () -> userService.removeAdminRole(TEST_ID_1));
    verify(userRepository).findById(TEST_ID_1);
    verify(roleService, never()).findRoleByName(any());
    verify(userRepository, never()).save(any());
  }
  
  @Test
  void testRemoveAdminRole_RoleNotFound() {
    // Arrange
    User user = new User();
    user.setId(TEST_ID_1);
    
    when(userRepository.findById(TEST_ID_1)).thenReturn(Optional.of(user));
    when(roleService.findRoleByName(UserRoleEnum.ADMIN)).thenReturn(Optional.empty());
    
    // Act & Assert
    assertThrows(RoleNotFoundException.class, () -> userService.removeAdminRole(TEST_ID_1));
    verify(userRepository).findById(TEST_ID_1);
    verify(roleService).findRoleByName(UserRoleEnum.ADMIN);
    verify(userRepository, never()).save(any());
  }
  @Test
  void testDeleteUser_UserExists() {
    // Arrange
    when(userRepository.existsById(TEST_ID_1)).thenReturn(true);
    
    // Act
    userService.deleteUser(TEST_ID_1);
    
    // Assert
    verify(userRepository).existsById(TEST_ID_1);
    verify(userRepository).deleteById(TEST_ID_1);
  }
  
  @Test
  void testDeleteUser_UserNotFound() {
    // Arrange
    when(userRepository.existsById(TEST_ID_1)).thenReturn(false);
    
    // Act & Assert
    assertThrows(InvalidUserIdException.class, () -> userService.deleteUser(TEST_ID_1));
    verify(userRepository).existsById(TEST_ID_1);
    verify(userRepository, never()).deleteById(anyLong());
  }

  //ч

  @Test
  void testRegisterUser() {
    // Arrange
    UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
    userRegistrationDto.setUsername(USERNAME);
    userRegistrationDto.setPassword(PASSWORD);
    userRegistrationDto.setEmail(EMAIL);


    User user = new User();
    user.setUsername(USERNAME);
    user.setPassword(PASSWORD);
    user.setEmail(EMAIL);
    user.setFirstName(FIRST_NAME);
    user.setLastName(LAST_NAME);
    user.setPhone(PHONE);

    when(modelMapper.map(userRegistrationDto, User.class)).thenReturn(user);
    when(passwordEncoder.encode(PASSWORD)).thenReturn("encodedPassword");
    Role userRole = new Role();
    userRole.setName(UserRoleEnum.USER);
    when(roleService.findRoleByName(UserRoleEnum.USER)).thenReturn(Optional.of(userRole));

    // Act
    userService.registerUser(userRegistrationDto);

    // Assert
    verify(userRepository).save(user);
    assertEquals("encodedPassword", user.getPassword());
    assertEquals(1, user.getRoles().size());
    assertTrue(user.getRoles().contains(userRole));
  }

  @Test
  void testGetCurrentUserByContext_Success() {
    // Arrange
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.getName()).thenReturn(USERNAME);
    SecurityContextHolder.setContext(securityContext);

    User user = new User();
    user.setUsername(USERNAME);
    when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));

    // Act
    User result = userService.getCurrentUserByContext();

    // Assert
    assertNotNull(result);
    assertEquals(USERNAME, result.getUsername());
    verify(userRepository).findByUsername(USERNAME);
  }

  @Test
  void testGetCurrentUserByContext_NoAuthentication() {
    // Arrange
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(null);
    SecurityContextHolder.setContext(securityContext);

    // Act
    User result = userService.getCurrentUserByContext();

    // Assert
    assertNull(result);
    verify(userRepository, never()).findByUsername(anyString());
  }

//  @Test
//  void testReauthenticateUser() {
//    // Arrange
//    UserDetails userDetails = mock(UserDetails.class);
//    when(parkingUserDetailsService.loadUserByUsername(USERNAME)).thenReturn(userDetails);
//
//    // Mock the SecurityContext and Authentication
//    Authentication authentication = mock(Authentication.class);
//    SecurityContext securityContext = mock(SecurityContext.class);
//    when(securityContext.getAuthentication()).thenReturn(authentication);
//    SecurityContextHolder.setContext(securityContext);
//
//    // Act
//    userService.reauthenticateUser(USERNAME);
//
//    // Assert
//    verify(parkingUserDetailsService).loadUserByUsername(USERNAME);
//    Authentication updatedAuthentication = SecurityContextHolder.getContext().getAuthentication();
//    assertNotNull(updatedAuthentication);
//    assertEquals(userDetails, updatedAuthentication.getPrincipal());
//  }

}

