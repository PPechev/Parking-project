package bg.softuni.parking.Service;

import bg.softuni.parking.model.dto.UserRegistrationDto;
import bg.softuni.parking.model.entities.User;
import bg.softuni.parking.repository.UserRepository;
import org.modelmapper.ModelMapper;
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
}
