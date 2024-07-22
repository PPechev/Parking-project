package bg.softuni.parking.web;

import bg.softuni.parking.Service.UserService;
import bg.softuni.parking.model.dto.UserUpdateDto;
import bg.softuni.parking.model.user.ParkingUserDetails;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("userUpdateDto")
    public UserUpdateDto userUpdateDto(@AuthenticationPrincipal ParkingUserDetails userDetails) {
        UserUpdateDto dto = new UserUpdateDto();
        dto.setUsername(userDetails.getUsername());
        dto.setEmail(userDetails.getUsername()); // Assuming email is stored in username, adjust if necessary
        dto.setPhone(userDetails.getPhone());
        dto.setFirstName(userDetails.getFirstName());
        dto.setLastName(userDetails.getLastName());
        return dto;
    }

    @GetMapping("/profile")
    public String showProfilePage(@AuthenticationPrincipal ParkingUserDetails userDetails, Model model) {
        if (!model.containsAttribute("userUpdateDto")) {
            UserUpdateDto userUpdateDto = userUpdateDto(userDetails);
            model.addAttribute("userUpdateDto", userUpdateDto);
        }
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid @ModelAttribute("userUpdateDto") UserUpdateDto userUpdateDto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes,
                                @AuthenticationPrincipal ParkingUserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userUpdateDto", userUpdateDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userUpdateDto", bindingResult);
            return "redirect:/profile";
        }

        userService.updateUser(userUpdateDto, userDetails.getUsername());
        return "redirect:/profile?success";
    }
}
