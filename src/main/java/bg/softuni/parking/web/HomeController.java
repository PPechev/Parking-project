package bg.softuni.parking.web;

import bg.softuni.parking.model.entities.User;
import bg.softuni.parking.model.user.ParkingUserDetails;
import bg.softuni.parking.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class HomeController {


    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")

    public String home(Model model , @AuthenticationPrincipal UserDetails userDetails) {


        if (userDetails instanceof ParkingUserDetails parkingUserDetails) {

            model.addAttribute("welcomeMessage", parkingUserDetails.getFullName());
        }else {

            model.addAttribute("welcomeMessage", "Anonymous");
        }

        return "home";
    }


}
