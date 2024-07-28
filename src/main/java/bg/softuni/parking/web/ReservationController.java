package bg.softuni.parking.web;

import bg.softuni.parking.model.dto.ReservationDto;
import bg.softuni.parking.model.dto.VehicleDto;
import bg.softuni.parking.model.entities.Reservation;
import bg.softuni.parking.service.ReservationService;
import bg.softuni.parking.service.VehicleService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final VehicleService vehicleService;

    public ReservationController(ReservationService reservationService, VehicleService vehicleService) {
        this.reservationService = reservationService;
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public String viewReservations(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<ReservationDto> reservations = reservationService.getUserReservations(userDetails.getUsername());
        model.addAttribute("reservations", reservations);
        return "reservations";
    }

    @GetMapping("/edit/{id}")
    public String editReservation(@PathVariable Long id, Model model) {
        ReservationDto reservation = reservationService.getReservationById(id);
        model.addAttribute("reservation", reservation);
        return "reservation-edit";
    }

    @PostMapping("/update")
    public String updateReservation(@ModelAttribute ReservationDto reservationDto) {
        reservationService.updateReservation(reservationDto);
        return "redirect:/reservations";
    }

    @GetMapping("/add")
    public String addReservationForm(Model model,@AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("reservation", new ReservationDto());
        List<VehicleDto> vehicles = vehicleService.getUserVehicles(userDetails.getUsername());
        model.addAttribute("vehicles", vehicles);
        return "reservation-adding";
    }

    @PostMapping("/add")
    public String addReservation(@ModelAttribute ReservationDto reservationDto, @AuthenticationPrincipal UserDetails userDetails) {
        reservationService.addReservation(reservationDto, userDetails.getUsername());
        return "redirect:/reservations";
    }

    @GetMapping("/all-reservations")
    public String getAllReservations(Model model) {
        List<Reservation> reservations = reservationService.findAll();
        model.addAttribute("reservations", reservations);
        return "all-reservations";
    }

    @GetMapping("/new")
    public String showNewReservationForm(Model model ,@AuthenticationPrincipal UserDetails userDetails) {
        User currentUser = getCurrentUser(); // Метод за извличане на текущия потребител
        List<VehicleDto> vehicles = vehicleService.getUserVehicles(userDetails.getUsername());
        ReservationDto reservation = new ReservationDto();
        model.addAttribute("reservation", reservation);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("vehicles", vehicles);
        return "reservation-adding";
    }
    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

}
