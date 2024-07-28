//package bg.softuni.parking.web;
//
//import bg.softuni.parking.Service.UserService;
//import bg.softuni.parking.model.dto.UserRegistrationDto;
//
//import jakarta.validation.Valid;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//public class RegisterController {
//
//
//    private final UserService userService;
//
//    public RegisterController(UserService userService) {
//        this.userService = userService;
//    }
//
//
//    @ModelAttribute("registerDTO")
//    public UserRegistrationDto registerDTO() {
//        return new UserRegistrationDto();
//    }
//
//
//    @GetMapping("/register")
//    public String register() {
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String registerSubmit(@Valid UserRegistrationDto userRegistrationDto,
//                                 BindingResult bindingResult,
//                                 RedirectAttributes redirectAttributes) {
//
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("registerDTO", userRegistrationDto);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.UserRegistrationDto", bindingResult);
//            return "redirect:/register";
//        }
//        // Логика за регистрация на потребител
//        userService.registerUser(userRegistrationDto);
//        return "redirect:/login";
//    }
//
//
//}

package bg.softuni.parking.web;

import bg.softuni.parking.service.UserService;
import bg.softuni.parking.model.dto.UserRegistrationDto;

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
    public String registerSubmit(@Valid @ModelAttribute("registerDTO") UserRegistrationDto userRegistrationDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("registerDTO", userRegistrationDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerDTO", bindingResult);
            return "redirect:/register";
        }

        userService.registerUser(userRegistrationDto);
        return "redirect:/login";
    }
}
