//package bg.softuni.parking.web;
//
//import bg.softuni.parking.Service.ReservationService;
//import bg.softuni.parking.model.entities.Reservation;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//
//@Controller
//public class ReservationController {
//
//    private final ReservationService reservationService;
//
//    public ReservationController(ReservationService reservationService) {
//        this.reservationService = reservationService;
//    }
//
//
//    @GetMapping("/reservations")
//    public String listReservations(Model model) {
//        List<Reservation> reservations = reservationService.findAll();
//        model.addAttribute("reservations", reservations);
//        return "reservations";
//    }
//}



package bg.softuni.parking.web;

import bg.softuni.parking.Service.ReservationService;
import bg.softuni.parking.model.entities.Reservation;
import bg.softuni.parking.model.user.ParkingUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/reservations")
    public String getUserReservations(@RequestParam String username, Model model) {
        List<Reservation> reservations = reservationService.findReservationsByUsername(username);
        model.addAttribute("reservations", reservations);
        return "reservations";
    }

    @GetMapping("/all-reservations")
    public String getAllReservations(Model model) {
        List<Reservation> reservations = reservationService.findAll();
        model.addAttribute("reservations", reservations);
        return "all-reservations";
    }

}
