package bg.softuni.parking.web;

import bg.softuni.parking.model.dto.UserRegistrationDto;
import bg.softuni.parking.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class RegisterControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void testRegisterForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("registerDTO"));
    }

    @Test
    void testRegisterSubmit_PasswordsMismatch() throws Exception {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setConfirmPassword("differentPassword");

        mockMvc.perform(post("/register")
                        .flashAttr("registerDTO", userRegistrationDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"))
                .andExpect(flash().attributeExists("registerDTO"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.registerDTO"));

        verify(userService, never()).registerUser(any(UserRegistrationDto.class));
    }

    @Test
    void testRegisterSubmit_UsernameExists() throws Exception {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setUsername("existingUser");
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setConfirmPassword("password");

        when(userService.usernameExists(userRegistrationDto.getUsername())).thenReturn(true);

        mockMvc.perform(post("/register")
                        .flashAttr("registerDTO", userRegistrationDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"))
                .andExpect(flash().attributeExists("registerDTO"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.registerDTO"));

        verify(userService, never()).registerUser(any(UserRegistrationDto.class));
    }

    @Test
    void testRegisterSubmit_EmailExists() throws Exception {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setUsername("newUser");
        userRegistrationDto.setEmail("existingEmail@example.com");
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setConfirmPassword("password");

        when(userService.emailExists(userRegistrationDto.getEmail())).thenReturn(true);

        mockMvc.perform(post("/register")
                        .flashAttr("registerDTO", userRegistrationDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/register"))
                .andExpect(flash().attributeExists("registerDTO"))
                .andExpect(flash().attributeExists("org.springframework.validation.BindingResult.registerDTO"));

        verify(userService, never()).registerUser(any(UserRegistrationDto.class));
    }

    @Test
    void testRegisterSubmit_Success() throws Exception {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        userRegistrationDto.setUsername("newUser");
        userRegistrationDto.setEmail("newEmail@example.com");
        userRegistrationDto.setPassword("password");
        userRegistrationDto.setConfirmPassword("password");

        when(userService.usernameExists(userRegistrationDto.getUsername())).thenReturn(false);
        when(userService.emailExists(userRegistrationDto.getEmail())).thenReturn(false);

        mockMvc.perform(post("/register")
                        .flashAttr("registerDTO", userRegistrationDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(userService).registerUser(any(UserRegistrationDto.class));
    }
}
