package bg.softuni.parking.web;

import bg.softuni.parking.model.entities.User;
import bg.softuni.parking.service.ParkingSpotService;
import bg.softuni.parking.model.entities.ParkingSpot;
import bg.softuni.parking.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;
    private final UserService userService;

    public ParkingSpotController(ParkingSpotService parkingSpotService, UserService userService) {
        this.parkingSpotService = parkingSpotService;
        this.userService = userService;
    }
//
//    @GetMapping("/parking-spots")
//        public String listParkingSpots(Model model) {
//            List<ParkingSpot> parkingSpots = parkingSpotService.findAll();
//            model.addAttribute("parkingSpots", parkingSpots);
//            return "parking-spots";
//        }


//    @GetMapping("/parking-spots")
//    public String getParkingSpots(Model model, @AuthenticationPrincipal UserDetails userDetails) {
//        boolean isLoggedIn = userDetails != null;
//        boolean hasVehicles = false;
//
//        if (isLoggedIn) {
//            // Проверете дали потребителят има създадени превозни средства
//            Optional<User> optionalUser = userService.findByUsername(userDetails.getUsername());
//            if (optionalUser.isPresent()) {
//                User user = optionalUser.get();
//                hasVehicles = !user.getVehicles().isEmpty();
//            }
//        }
//
//        List<ParkingSpot> parkingSpots = parkingSpotService.findAll();
//        model.addAttribute("parkingSpots", parkingSpots);
//        model.addAttribute("isLoggedIn", isLoggedIn);
//        model.addAttribute("hasVehicles", hasVehicles);
//
//        return "parking-spots";
//    }

    @GetMapping("/parking-spots")
    public String getParkingSpots(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        boolean isLoggedIn = userDetails != null;
        boolean hasVehicles = false;

        if (isLoggedIn) {
            Optional<User> optionalUser = userService.findByUsername(userDetails.getUsername());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                hasVehicles = !user.getVehicles().isEmpty();
            }
        }

        List<ParkingSpot> parkingSpots = parkingSpotService.findAll();
        model.addAttribute("parkingSpots", parkingSpots);
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("hasVehicles", hasVehicles);

        return "parking-spots";
    }
    }

