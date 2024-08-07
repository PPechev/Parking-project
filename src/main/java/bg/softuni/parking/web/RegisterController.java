package bg.softuni.parking.web;

import bg.softuni.parking.model.dto.UserRegistrationDto;
import bg.softuni.parking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("registerDTO")
    public UserRegistrationDto registerDTO() {
        return new UserRegistrationDto();
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(
            @Valid @ModelAttribute("registerDTO") UserRegistrationDto userRegistrationDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (!userRegistrationDto.getPassword().equals(userRegistrationDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Паролите не съвпадат");
        }
        if (userService.usernameExists(userRegistrationDto.getUsername())) {
            bindingResult.rejectValue("username", "error.username", "Потребителското име вече съществува");
        }
        if (userService.emailExists(userRegistrationDto.getEmail())) {
            bindingResult.rejectValue("email", "error.email", "Потребител с този имейл вече съществува");
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerDTO", userRegistrationDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerDTO", bindingResult);
            return "redirect:/register";
        }

        userService.registerUser(userRegistrationDto);
        return "redirect:/login";
    }
}
