

package bg.softuni.parking.service;

import bg.softuni.parking.model.dto.*;
import bg.softuni.parking.model.entities.Reservation;
import bg.softuni.parking.model.entities.Role;
import bg.softuni.parking.model.entities.User;
import bg.softuni.parking.model.enums.UserRoleEnum;
import bg.softuni.parking.model.user.ParkingUserDetails;
import bg.softuni.parking.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final ParkingUserDetailsService parkingUserDetailsService;
    private final VehicleService vehicleService;


    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, RoleService roleService, ParkingUserDetailsService parkingUserDetailsService, VehicleService vehicleService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.parkingUserDetailsService = parkingUserDetailsService;
        this.vehicleService = vehicleService;
    }


    public void registerUser(UserRegistrationDto userRegistrationDto) {
        userRepository.save(map(userRegistrationDto));
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    private User map(UserRegistrationDto userRegistrationDto) {


        User mappedUser = modelMapper.map(userRegistrationDto, User.class);

        mappedUser.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));

        Role userRole = roleService.findRoleByName(UserRoleEnum.USER).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        mappedUser.setRoles(new HashSet<>(Set.of(userRole)));

        return mappedUser;
    }

//    public void updateUser(UserUpdateDto userUpdateDto) {
//        User user = userRepository.findByUsername(userUpdateDto.getUsername())
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        user.setUsername(userUpdateDto.getUsername());
//        user.setEmail(userUpdateDto.getEmail());
//        user.setPhone(userUpdateDto.getPhone());
//        user.setFirstName(userUpdateDto.getFirstName());
//        user.setLastName(userUpdateDto.getLastName());
//
//
//        userRepository.save(user);
//    }

    @Transactional
    public void updateUser(UserUpdateDto userUpdateDto) {
        User user = getCurrentUserByContext();

        user.setUsername(userUpdateDto.getUsername());
        user.setEmail(userUpdateDto.getEmail());
        user.setPhone(userUpdateDto.getPhone());
        user.setFirstName(userUpdateDto.getFirstName());
        user.setLastName(userUpdateDto.getLastName());


        userRepository.saveAndFlush(user);
        reauthenticateUser(userUpdateDto.getUsername());
    }


    public User getCurrentUserByContext() {
        Principal currentUser = SecurityContextHolder.getContext().getAuthentication();
        if (currentUser == null) {
            return null;
        }
        return this.getByUsername(currentUser.getName());
    }

    public void reauthenticateUser(String newUsername) {
        UserDetails userDetails = parkingUserDetailsService.loadUserByUsername(newUsername);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public UserProfileDto getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        UserProfileDto dto = modelMapper.map(user, UserProfileDto.class);

        return dto;

    }

    private ReservationDto convertToReservationDto(Reservation reservation) {
        ReservationDto dto = new ReservationDto();
        dto.setId(reservation.getId());
        dto.setParkingSpotLocation(reservation.getParkingSpot().getLocation());
        dto.setStartTime(reservation.getStartTime());
        dto.setEndTime(reservation.getEndTime());
        return dto;
    }


    public void updateUserProfile(UserProfileDto userProfileDto) {
        User user = userRepository.findByUsername(userProfileDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEmail(userProfileDto.getEmail());
        user.setFirstName(userProfileDto.getFirstName());
        user.setLastName(userProfileDto.getLastName());
        user.setPhone(userProfileDto.getPhone());
        userRepository.save(user);
    }


    public void changePassword(String username, ChangePasswordDto changePasswordDto) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // TODO проверка дали новата парола не е същата като старата , също така дали новата парола е еднаква в двете полета
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);
    }

    public void changeEmail(String username, ChangeEmailDto changeEmailDto) {
        User user = userRepository.findByUsername(username).orElseThrow();
        user.setEmail(changeEmailDto.getNewEmail());
        userRepository.save(user);
    }
//  VANYA REPLACED
//  public User getCurrentUser() {
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    return (User) authentication.getPrincipal();
//  }

    public ParkingUserDetails getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof ParkingUserDetails) {
            return (ParkingUserDetails) principal;
        } else {
            throw new IllegalStateException("Current user is not of type ParkingUserDetails");
        }
    }

    public List<UserDto> getAllUsersWithReservations() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> {
                    UserDto userDto = new UserDto();
                    userDto.setUsername(user.getUsername());
                    userDto.setReservations(user.getReservations()
                            .stream()
                            .map(reservation -> {
                                ReservationDto reservationDto = new ReservationDto();
                                reservationDto.setStartTime(reservation.getStartTime());
                                reservationDto.setEndTime(reservation.getEndTime());
                                reservationDto.setVehicleLicensePlate(vehicleService.getVehicleById(reservation.getVehicleId()).getLicensePlate());
                                reservationDto.setParkingSpotLocation(reservation.getParkingSpot().getLocation());
                                return reservationDto;
                            })
                            .collect(Collectors.toList()));
                    return userDto;
                })
                .collect(Collectors.toList());
    }

    //  addition
    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }


    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean checkUniqueUsername(String newUsername, String currentUsername) {
        return userRepository.existsByUsernameAndNotCurrentUsername(newUsername, currentUsername);
    }

    public boolean checkUniqueEmail(String newEmail, String currentEmail) {
        return userRepository.existsByEmailAndNotCurrentEmail(newEmail, currentEmail);
    }

    public boolean isPasswordCorrect(String storedPasswordHash, String enteredOldPassword) {
        return passwordEncoder.matches(enteredOldPassword, storedPasswordHash);
    }

    public boolean passwordsAreSame(String password, String newPassword) {
        return passwordEncoder.matches(newPassword, password);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User findByUuid(String owner) {
        return userRepository.findByUuid(owner);
    }




    public List<UserWithRolesDto> getAllUsersWithRoles() {
        return userRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private UserWithRolesDto convertToDto(User user) {
        UserWithRolesDto dto = new UserWithRolesDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
        return dto;
    }

    public void addAdminRole(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Role role = roleService.findRoleByName(UserRoleEnum.ADMIN).orElseThrow(() -> new IllegalArgumentException("Invalid role name"));
        user.getRoles().add(role);
        userRepository.save(user);
    }

    public void removeAdminRole(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Role role = roleService.findRoleByName(UserRoleEnum.ADMIN).orElseThrow(() -> new IllegalArgumentException("Invalid role name"));
        user.getRoles().remove(role);
        userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

}