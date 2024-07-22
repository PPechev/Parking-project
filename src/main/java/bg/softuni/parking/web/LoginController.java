//package bg.softuni.parking.web;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@Controller
//public class LoginController {
//
//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }
//
//    @PostMapping("/login")
//    public String loginSubmit() {
//        // Логика за проверка на логин данни
//        return "redirect:/";
//    }
//}

package bg.softuni.parking.web;

import bg.softuni.parking.Service.UserService;
import bg.softuni.parking.model.dto.UserLoginDto;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("loginDTO")
    public UserLoginDto loginDTO() {
        return new UserLoginDto();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@Valid @ModelAttribute("loginDTO") UserLoginDto userLoginDto,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("loginDTO", userLoginDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.loginDTO", bindingResult);
            return "redirect:/login";
        }

//        if (!userService.isUserExists(userLoginDto)){
//            throw new RuntimeException("Username or password is incorrect");
//        }
        // Логика за проверка на потребителското име и паролата
        // Ако валидацията е успешна, пренасочване към началната страница


        return "redirect:/home";
    }
}
