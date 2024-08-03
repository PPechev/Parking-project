package bg.softuni.parking.web;

import bg.softuni.parking.model.dto.ReservationDto;
import bg.softuni.parking.model.dto.UserDto;
import bg.softuni.parking.model.dto.VehicleDto;
import bg.softuni.parking.model.entities.ParkingSpot;
import bg.softuni.parking.model.entities.Reservation;
import bg.softuni.parking.model.entities.User;
import bg.softuni.parking.model.entities.Vehicle;
import bg.softuni.parking.service.ParkingSpotService;
import bg.softuni.parking.service.ReservationService;
import bg.softuni.parking.service.UserService;
import bg.softuni.parking.service.VehicleService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ReservationService reservationService;
    private final ParkingSpotService parkingSpotService;
    private final VehicleService vehicleService;

    public AdminController(UserService userService, ReservationService reservationService, ParkingSpotService parkingSpotService, VehicleService vehicleService) {
        this.userService = userService;
        this.reservationService = reservationService;
        this.parkingSpotService = parkingSpotService;
        this.vehicleService = vehicleService;
    }

//    @GetMapping("/all-reservations")
//    public String viewAllReservations(Model model) {
////        List<UserDto> users = userService.getAllUsersWithReservations();
////        model.addAttribute("users", users);
//
//        List<Reservation> reservations = reservationService.findAll();
//        model.addAttribute("reservations", reservations);
//
//        return "all-reservations";
//    }

    @GetMapping("/all-reservations")
    public String getAllReservations(Model model) {
        List<Reservation> reservations = reservationService.findAll();
        model.addAttribute("reservations", reservations);
        return "all-reservations";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteReservation(@PathVariable("id") Long id) {
        reservationService.deleteReservation(id);
        return "redirect:/admin/all-reservations";
    }
//
//    @Transactional
//    @GetMapping("/edit/{id}")
//    public String editVehicle(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
////        ReservationDto reservation = reservationService.getFormattedReservationById(id);
//        VehicleDto vehicle = vehicleService.getVehicleById(id);
////        parkingSpotService.makeSpotAvailable(reservation.getParkingSpotLocation());
//
//        List<VehicleDto> vehicles = vehicleService.getUserVehicles(userDetails.getUsername());
//        List<ParkingSpot> availableParkingSpots = parkingSpotService.findAllAvailable();
//
////        model.addAttribute("reservation", reservation);
//        model.addAttribute("vehicles", vehicles);
////        model.addAttribute("availableParkingSpots", availableParkingSpots);
//
//        return "reservation-edit";
//    }
//
//
//    @PostMapping("/update")
//    public String updateReservation(@ModelAttribute ReservationDto reservationDto) {
//        reservationService.updateReservation(reservationDto);
//        return "redirect:/admin/all-reservations";
//    }

    @GetMapping("/edit/{id}")
    public String editVehicle(@PathVariable Long id, Model model) {
        VehicleDto vehicle = vehicleService.getVehicleById(id);
        model.addAttribute("vehicle", vehicle);
        return "vehicles-edit";
    }

    @PostMapping("/update")
    public String updateVehicle(@ModelAttribute VehicleDto vehicleDto) {
        vehicleService.updateVehicle(vehicleDto);
        return "redirect:/all-vehicles";
    }

    @GetMapping("/all-vehicles")
    public String getAllVehicles(Model model) {
        List<Vehicle> vehicles = vehicleService.findAll();
        model.addAttribute("vehicles", vehicles);
        return "all-vehicles";
    }
    @DeleteMapping("vehicles/delete/{id}")
    public String deleteVehicle(@PathVariable Long id, Model model) {

        vehicleService.deleteVehicle(id);
        return "redirect:/all-vehicles";
    }


    @GetMapping("/all-users")
    public String getAllUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "all-users";
    }

}
