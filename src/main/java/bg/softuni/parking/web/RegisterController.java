package bg.softuni.parking.web;

import bg.softuni.parking.Service.UserService;
import bg.softuni.parking.model.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {


    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }


//    @GetMapping("/register")
//    public String register() {
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String registerSubmit(@ModelAttribute User user) {
//        // Логика за регистрация на потребител
//        userService.saveUser(user);
//        return "redirect:/login";
//    }


    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute User user) {
        // Логика за проверка на паролите и съхранение на потребител
        if (userService.findByEmail(user.getEmail()) != null) {
            // имейлът вече съществува
            return "redirect:/register?error=email";
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            // потребителското име вече съществува
            return "redirect:/register?error=username";
        }
        userService.saveUser(user);
        return "redirect:/login";
    }
}
