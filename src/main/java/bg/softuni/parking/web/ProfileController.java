//
//
//package bg.softuni.parking.web;
//
//import bg.softuni.parking.Service.UserService;
//import bg.softuni.parking.model.dto.UserProfileDto;
//import bg.softuni.parking.model.dto.ChangeEmailDto;
//import bg.softuni.parking.model.dto.ChangePasswordDto;
//import bg.softuni.parking.model.user.ParkingUserDetails;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import jakarta.validation.Valid;
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
////    @GetMapping("/profile")
////    public String profile(@AuthenticationPrincipal ParkingUserDetails userDetails, Model model) {
////        UserProfileDto userProfileDto = userService.getUserProfile(userDetails.getUsername());
////        model.addAttribute("user", userProfileDto);
////        return "profile";
////    }
//
//    @GetMapping("/profile")
//    public String profile(@AuthenticationPrincipal ParkingUserDetails userDetails, Model model) {
//        UserProfileDto userProfileDto = userService.getUserProfile(userDetails.getUsername());
//        model.addAttribute("user", userProfileDto);
//        return "profile";
//    }
//
//    @PostMapping("/profile/update")
//    public String updateProfile(@Valid @ModelAttribute("user") UserProfileDto userProfileDto,
//                                BindingResult bindingResult,
//                                RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("user", userProfileDto);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
//            return "redirect:/profile";
//        }
//
//        userService.updateUserProfile(userProfileDto);
//        return "redirect:/profile";
//    }
//
//    @GetMapping("/change-password")
//    public String changePassword(Model model) {
//        model.addAttribute("changePasswordDto", new ChangePasswordDto());
//        return "change-password";
//    }
//
//    @PostMapping("/change-password")
//    public String changePassword(@Valid @ModelAttribute("changePasswordDto") ChangePasswordDto changePasswordDto,
//                                 BindingResult bindingResult,
//                                 @AuthenticationPrincipal ParkingUserDetails userDetails,
//                                 RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("changePasswordDto", changePasswordDto);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changePasswordDto", bindingResult);
//            return "redirect:/change-password";
//        }
//
//        userService.changePassword(userDetails.getUsername(), changePasswordDto);
//        return "redirect:/profile";
//    }
//
//    @GetMapping("/change-email")
//    public String changeEmail(Model model) {
//        model.addAttribute("changeEmailDto", new ChangeEmailDto());
//        return "change-email";
//    }
//
//    @PostMapping("/change-email")
//    public String changeEmail(@Valid @ModelAttribute("changeEmailDto") ChangeEmailDto changeEmailDto,
//                              BindingResult bindingResult,
//                              @AuthenticationPrincipal ParkingUserDetails userDetails,
//                              RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors()) {
//            redirectAttributes.addFlashAttribute("changeEmailDto", changeEmailDto);
//            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changeEmailDto", bindingResult);
//            return "redirect:/change-email";
//        }
//
//        userService.changeEmail(userDetails.getUsername(), changeEmailDto);
//        return "redirect:/profile";
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
