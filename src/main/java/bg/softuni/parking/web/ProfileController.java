package bg.softuni.parking.web;

import bg.softuni.parking.service.UserService;
import bg.softuni.parking.model.dto.ChangePasswordDto;
import bg.softuni.parking.model.dto.UserProfileDto;
import bg.softuni.parking.model.dto.UserUpdateDto;

import bg.softuni.parking.model.user.ParkingUserDetails;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String viewProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserProfileDto userProfileDto = userService.getUserProfile(userDetails.getUsername());
        model.addAttribute("user", userProfileDto);
        return "profile"; // Името на шаблона за информационната страница
    }

    @GetMapping("/profile/edit")
    public String editProfile(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        UserProfileDto userProfileDto = userService.getUserProfile(userDetails.getUsername());
        model.addAttribute("user", userProfileDto);
        return "profile-edit"; // Името на шаблона за редактиране
    }

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute UserUpdateDto userUpdateDto, @AuthenticationPrincipal UserDetails userDetails) {
        userService.updateUser(userUpdateDto);
        return "redirect:/profile";
    }

    @GetMapping("/change-password")
    public String changePassword(Model model) {
        model.addAttribute("changePasswordDto", new ChangePasswordDto());
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@Valid @ModelAttribute("changePasswordDto") ChangePasswordDto changePasswordDto,
                                 BindingResult bindingResult,
                                 @AuthenticationPrincipal ParkingUserDetails userDetails,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("changePasswordDto", changePasswordDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changePasswordDto", bindingResult);
            return "redirect:/change-password";
        }

        userService.changePassword(userDetails.getUsername(), changePasswordDto);
        return "redirect:/profile";
    }

}
