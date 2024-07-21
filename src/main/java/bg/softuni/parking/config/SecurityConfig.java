package bg.softuni.parking.config;

import bg.softuni.parking.Service.ParkingUserDetailsService;
import bg.softuni.parking.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity  httpSecurity) throws Exception {

        return httpSecurity.authorizeHttpRequests(

                authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/" ,"/login" ,"register","/parking-spots" ).permitAll()
                                .anyRequest()
                                .authenticated()
        )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/" , true)
//                                .failureForwardUrl("parking-spots")
                        )
                .logout(
                        logout ->
                                logout
                                        .logoutUrl("/logout")
                                        .logoutSuccessUrl("/")
                                        .invalidateHttpSession(true)
                ).build();
    }


    @Bean
    public ParkingUserDetailsService userDetailsService(UserRepository userRepository) {

        return new ParkingUserDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){


        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }
}
