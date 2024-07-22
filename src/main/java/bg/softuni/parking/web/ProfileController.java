//package bg.softuni.parking.web;
//
//import bg.softuni.parking.Service.UserService;
//import bg.softuni.parking.model.dto.UserUpdateDto;
//import bg.softuni.parking.model.user.ParkingUserDetails;
//import jakarta.validation.Valid;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//public class ProfileController {
//
//    private final UserService userService;
//
//    public ProfileController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @ModelAttribute("userUpdateDto")
//    public UserUpdateDto userUpdateDto(@AuthenticationPrincipal ParkingUserDetails userDetails) {
//        UserUpdateDto dto = new UserUpdateDto();
//        dto.setUsername(userDetails.getUsername());
//        dto.setEmail(userDetails.getUsername()); // Assuming email is stored in username, adjust if necessary
//        dto.setPhone(userDetails.getPhone());
//        dto.setFirstName(userDetails.getFirstName());
//        dto.setLastName(userDetails.getLastName());
//        return dto;
//    }
//
//    @GetMapping("/profile")
//    public String showProfilePage(@AuthenticationPrincipal ParkingUserDetails userDetails, Model model) {
//        if (!model.containsAttribute("userUpdateDto")) {
//            UserUpdateDto userUpdateDto = userUpdateDto(userDetails);
//            model.addAttribute("userUpdateDto", userUpdateDto);
//        }
//        return "profile";
//    }
//
//    @PostMapping("/profile")
//    public String updateProfile(@Valid @ModelAttribute("userUpdateDto") UserUpdateDto userUpdateDto,
//                                BindingResult bindingResult,
//                                RedirectAttributes redirectAttributes,
//                                @AuthenticationPrincipal ParkingUserDetails userDetails) {
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("userUpdateDto", userUpdateDto);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userUpdateDto", bindingResult);
//            return "redirect:/profile";
//        }
//
//        userService.updateUser(userUpdateDto, userDetails.getUsername());
//        return "redirect:/profile?success";
//    }
//}



package bg.softuni.parking.web;

import bg.softuni.parking.Service.UserService;
import bg.softuni.parking.model.dto.UserProfileDto;
import bg.softuni.parking.model.dto.ChangePasswordDto;
import bg.softuni.parking.model.user.ParkingUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal ParkingUserDetails userDetails, Model model) {
        UserProfileDto userProfileDto = userService.getUserProfile(userDetails.getUsername());
        model.addAttribute("user", userProfileDto);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@Valid @ModelAttribute("user") UserProfileDto userProfileDto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", userProfileDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            return "redirect:/profile";
        }

        userService.updateUserProfile(userProfileDto);
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
