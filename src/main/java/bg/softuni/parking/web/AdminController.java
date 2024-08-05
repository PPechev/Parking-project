package bg.softuni.parking.web;

import bg.softuni.parking.model.dto.BankCardDto;
import bg.softuni.parking.model.dto.ReservationDto;
import bg.softuni.parking.model.dto.UserWithRolesDto;
import bg.softuni.parking.model.dto.reservationAdminView.ReservationAdminView;

import bg.softuni.parking.model.dto.vehicle.VehicleView;
import bg.softuni.parking.model.dto.vehicle.VehicleViewAdmin;
import bg.softuni.parking.model.entities.ParkingSpot;

import bg.softuni.parking.service.*;
import jakarta.transaction.Transactional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ReservationService reservationService;
    private final ParkingSpotService parkingSpotService;
    private final VehicleService vehicleService;
    private final BankCardService bankCardService;

    public AdminController(UserService userService, ReservationService reservationService, ParkingSpotService parkingSpotService, VehicleService vehicleService, BankCardService bankCardService) {
        this.userService = userService;
        this.reservationService = reservationService;
        this.parkingSpotService = parkingSpotService;
        this.vehicleService = vehicleService;
        this.bankCardService = bankCardService;
    }



    @GetMapping("/all-reservations")
    public String getAllReservations(Model model) {
        List<ReservationAdminView> reservations = reservationService.findAllForAdmin();
        model.addAttribute("reservations", reservations);
        return "all-reservations";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteReservation(@PathVariable("id") Long id) {
        reservationService.deleteReservation(id);
        return "redirect:/admin/all-reservations";
    }

    @Transactional
    @GetMapping("/edit/{id}")
    public String editReservation(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        ReservationDto reservation = reservationService.getFormattedReservationById(id);
        parkingSpotService.makeSpotAvailable(reservation.getParkingSpotLocation());

        List<ParkingSpot> availableParkingSpots = parkingSpotService.findAllAvailable();
        List<BankCardDto> bankCardDto =  bankCardService.getBankCardsByUsername(userDetails.getUsername());


        model.addAttribute("reservation", reservation);
        model.addAttribute("availableParkingSpots", availableParkingSpots);
        model.addAttribute("vehicles", vehicleService.getUserVehicles(userService.getCurrentUser().getUuid()));
        model.addAttribute("bankingCards" ,bankCardDto );

        return "reservation-edit";
    }


    @PostMapping("/update")
    public String updateReservation(@ModelAttribute ReservationDto reservationDto) {
        reservationService.updateReservation(reservationDto);
        return "redirect:/admin/all-reservations";
    }








    @GetMapping("/all-vehicles")
    public String getAllVehicles(Model model) {
        List<VehicleViewAdmin> vehicles = vehicleService.findAll()
                .stream()
                .map(v -> {
                    String username = userService.findByUuid(v.getOwner()).getUsername();
                    v.setOwner(username);
                    return v;
                })
                .collect(Collectors.toList());
        model.addAttribute("vehicles", vehicles);
        return "all-vehicles";
    }


    @GetMapping("/edit-vehicle/{id}")
    public String editVehicle(@PathVariable Long id, Model model) {
        VehicleView vehicle = vehicleService.getVehicleById(id);
        model.addAttribute("vehicle", vehicle);
        return "vehicles-edit";
    }


    @DeleteMapping("vehicles/delete/{id}")
    public String deleteVehicle(@PathVariable Long id, Model model) {

        vehicleService.deleteVehicle(id);
        return "redirect:/admin/all-vehicles";
    }





    @GetMapping("/all-users")
    public String viewAllUsers(Model model) {
        List<UserWithRolesDto> users = userService.getAllUsersWithRoles();
        model.addAttribute("users", users);
        return "all-users";
    }

    @PostMapping("/add-admin-role")
    public String addAdminRole(@RequestParam Long userId) {
        userService.addAdminRole(userId);
        return "redirect:/admin/all-users";
    }

    @PostMapping("/remove-admin-role")
    public String removeAdminRole(@RequestParam Long userId) {
        userService.removeAdminRole(userId);
        return "redirect:/admin/all-users";
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin/all-users";
    }
}
