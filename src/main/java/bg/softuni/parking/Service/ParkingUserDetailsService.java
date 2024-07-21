package bg.softuni.parking.Service;

import bg.softuni.parking.model.entities.Role;
import bg.softuni.parking.model.entities.User;
import bg.softuni.parking.model.enums.UserRoleEnum;
import bg.softuni.parking.model.user.ParkingUserDetails;
import bg.softuni.parking.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ParkingUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public ParkingUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .map(ParkingUserDetailsService::map).orElseThrow(() ->
                        new UsernameNotFoundException("User with username " + username + " not found"));
    }

    private static UserDetails map(User user) {


        return new ParkingUserDetails(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(Role::getName).map(ParkingUserDetailsService::mapped).toList(),
                user.getFirstName(),
                user.getLastName()
                );
    }
    private static GrantedAuthority mapped(UserRoleEnum role) {
        return new SimpleGrantedAuthority(
        "ROLE_" + role
        );
    }
}
