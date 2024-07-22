package bg.softuni.parking.Service;

import bg.softuni.parking.model.dto.UserLoginDto;
import bg.softuni.parking.model.dto.UserRegistrationDto;
import bg.softuni.parking.model.dto.UserUpdateDto;
import bg.softuni.parking.model.entities.User;
import bg.softuni.parking.repository.UserRepository;
import org.modelmapper.ModelMapper;
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



    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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

    public void updateUser(UserUpdateDto userUpdateDto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        user.setUsername(userUpdateDto.getUsername());
        user.setEmail(userUpdateDto.getEmail());
        user.setPhone(userUpdateDto.getPhone());
        user.setFirstName(userUpdateDto.getFirstName());
        user.setLastName(userUpdateDto.getLastName());

        if (userUpdateDto.getPassword() != null && !userUpdateDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        }

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
}
