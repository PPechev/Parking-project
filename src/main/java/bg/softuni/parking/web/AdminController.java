package bg.softuni.parking.web;

import bg.softuni.parking.model.dto.UserDto;
import bg.softuni.parking.model.entities.Reservation;
import bg.softuni.parking.service.ReservationService;
import bg.softuni.parking.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
//@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ReservationService reservationService;

    public AdminController(UserService userService, ReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @GetMapping("/all-reservations")
    public String viewAllReservations(Model model) {
//        List<UserDto> users = userService.getAllUsersWithReservations();
//        model.addAttribute("users", users);

        List<Reservation> reservations = reservationService.findAll();
        model.addAttribute("reservations", reservations);

        return "all-reservations";
    }
}
