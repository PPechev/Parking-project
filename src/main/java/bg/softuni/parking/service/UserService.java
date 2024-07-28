package bg.softuni.parking.service;

import bg.softuni.parking.model.dto.*;
import bg.softuni.parking.model.entities.Reservation;
import bg.softuni.parking.model.entities.User;
import bg.softuni.parking.model.entities.Vehicle;
import bg.softuni.parking.repository.UserRepository;
import bg.softuni.parking.repository.VehicleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final VehicleRepository vehicleRepository;



    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, VehicleRepository vehicleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.vehicleRepository = vehicleRepository;
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

        //TODO must create DTO and replace it instead of User !

        User mappedUser = modelMapper.map(userRegistrationDto, User.class);

        mappedUser.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        return mappedUser;
    }

    public void updateUser(UserUpdateDto userUpdateDto) {
        User user = userRepository.findByUsername(userUpdateDto.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setUsername(userUpdateDto.getUsername());
        user.setEmail(userUpdateDto.getEmail());
        user.setPhone(userUpdateDto.getPhone());
        user.setFirstName(userUpdateDto.getFirstName());
        user.setLastName(userUpdateDto.getLastName());



        userRepository.save(user);
    }

//    public boolean isUserExists(UserLoginDto userLoginDto) {
//
//        if (userLoginDto == null){
//            return false;
//        }
//        if (userLoginDto.getUsername() == null || userLoginDto.getUsername().isEmpty()) {
//            return false;
//        }
//        if (userLoginDto.getPassword() == null || userLoginDto.getPassword().isEmpty()) {
//            return false;
//        }
//        return true;
//    }



//    public UserProfileDto getUserProfile(String username) {
//        User user = userRepository.findByUsername(username).orElseThrow();
//        return modelMapper.map(user, UserProfileDto.class);
//    }
//
//    public UserProfileDto getUserProfile(String username) {
//        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        UserProfileDto dto = modelMapper.map(user, UserProfileDto.class);
//        dto.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
//        dto.setReservations(user.getReservations());
//        dto.setVehicles(user.getVehicles());
//        return dto;
//    }

    //
//    public UserProfileDto getUserProfile(String username) {
//        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        UserProfileDto dto = modelMapper.map(user, UserProfileDto.class);
//        dto.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()));
//        dto.setReservations(user.getReservations().stream().map(this::convertToReservationDto).collect(Collectors.toSet()));
//        dto.setVehicles(user.getVehicles());
//        return dto;
//    }
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

//    public void updateUserProfile(UserProfileDto userProfileDto) {
//        User user = userRepository.findByUsername(userProfileDto.getUsername()).orElseThrow();
//        user.setEmail(userProfileDto.getEmail());
//        user.setFirstName(userProfileDto.getFirstName());
//        user.setLastName(userProfileDto.getLastName());
//        user.setPhone(userProfileDto.getPhone());
//        userRepository.save(user);
//    }

//    public void updateUserProfile(UserProfileDto userProfileDto) {
//        User user = userRepository.findByUsername(userProfileDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        user.setEmail(userProfileDto.getEmail());
//        user.setFirstName(userProfileDto.getFirstName());
//        user.setLastName(userProfileDto.getLastName());
//        user.setPhone(userProfileDto.getPhone());
//        userRepository.save(user);
//    }

    public void updateUserProfile(UserProfileDto userProfileDto) {
        User user = userRepository.findByUsername(userProfileDto.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEmail(userProfileDto.getEmail());
        user.setFirstName(userProfileDto.getFirstName());
        user.setLastName(userProfileDto.getLastName());
        user.setPhone(userProfileDto.getPhone());
        userRepository.save(user);
    }

//
//    public void changePassword(String username, ChangePasswordDto changePasswordDto) {
//        User user = userRepository.findByUsername(username).orElseThrow();
//        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
//        userRepository.save(user);
//    }
//
//    public void changePassword(String username, ChangePasswordDto changePasswordDto) {
//        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
//        userRepository.save(user);
//    }

    public void changePassword(String username, ChangePasswordDto changePasswordDto) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);
    }

    public void changeEmail(String username, ChangeEmailDto changeEmailDto) {
        User user = userRepository.findByUsername(username).orElseThrow();
        user.setEmail(changeEmailDto.getNewEmail());
        userRepository.save(user);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    public void setSelectedVehicle(Long vehicleId) {
        User currentUser = getCurrentUser();
        currentUser.setSelectedVehicleId(vehicleId);
    }

    public Vehicle getSelectedVehicle() {
        User currentUser = getCurrentUser();
        Long selectedVehicleId = currentUser.getSelectedVehicleId();
        return vehicleRepository.findById(selectedVehicleId).orElse(null);
    }

}