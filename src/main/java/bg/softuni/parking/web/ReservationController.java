


package bg.softuni.parking.web;

import bg.softuni.parking.model.dto.BankCardDto;
import bg.softuni.parking.model.dto.NewReservationDto;
import bg.softuni.parking.model.dto.ReservationDto;
import bg.softuni.parking.model.dto.vehicle.VehicleView;
import bg.softuni.parking.model.entities.ParkingSpot;
import bg.softuni.parking.service.*;
import jakarta.transaction.Transactional;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final VehicleService vehicleService;
    private final ParkingSpotService parkingSpotService;
    private final UserService userService;
    private final BankCardService bankCardService;

    public ReservationController(ReservationService reservationService, VehicleService vehicleService, ParkingSpotService parkingSpotService, UserService userService, BankCardService bankCardService) {
        this.reservationService = reservationService;
        this.vehicleService = vehicleService;
        this.parkingSpotService = parkingSpotService;
        this.userService = userService;
        this.bankCardService = bankCardService;
    }

    @GetMapping
    public String viewReservations(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<ReservationDto> reservations = reservationService.getUserReservations(userService.getCurrentUser().getUsername());
        List<ReservationDto> validReservations = new ArrayList<>();
        for (ReservationDto reservation : reservations) {
            if (reservation.getStartTime().isAfter(LocalDateTime.now())) {
                validReservations.add(reservation);
            }
        }
        model.addAttribute("reservations", validReservations);
        return "reservations";
    }

    @Transactional
    @GetMapping("/edit/{id}")
    public String editReservation(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        ReservationDto reservation = reservationService.getFormattedReservationById(id);
        parkingSpotService.makeSpotAvailable(reservation.getParkingSpotLocation());

        List<VehicleView> vehicles = vehicleService.getUserVehicles(userService.getCurrentUser().getUuid());
        List<ParkingSpot> availableParkingSpots = parkingSpotService.findAllAvailable();
        List<BankCardDto> bankCardDto = bankCardService.getBankCardsByUsername(userDetails.getUsername());

        model.addAttribute("reservation", reservation);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("availableParkingSpots", availableParkingSpots);
        model.addAttribute("bankingCards", bankCardDto);


        return "reservation-edit";
    }


    @PostMapping("/update")
    public String updateReservation(@ModelAttribute ReservationDto reservationDto) {
        reservationService.updateReservation(reservationDto);
        return "redirect:/reservations";
    }

    @GetMapping("/add")
    public String addReservationForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("reservation", new ReservationDto());
        List<VehicleView> vehicles = vehicleService.getUserVehicles(userService.getCurrentUser().getUuid());
        List<ParkingSpot> availableParkingSpots = parkingSpotService.findAllAvailable();
        List<BankCardDto> bankCardDto = bankCardService.getBankCardsByUsername(userDetails.getUsername());


        model.addAttribute("vehicles", vehicles);
        model.addAttribute("availableParkingSpots", availableParkingSpots);
        model.addAttribute("bankingCards", bankCardDto);

        return "reservation-adding";
    }

    @PostMapping("/add")
    public String addReservation(@ModelAttribute ReservationDto reservationDto, @AuthenticationPrincipal UserDetails userDetails) {
        reservationService.addReservation(reservationDto, userDetails.getUsername());


        return "redirect:/reservations";
    }


    @GetMapping("/new")
    public String newReservationForm(@RequestParam("spotId") Long spotId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (vehicleService.getUserVehicles(userService.getCurrentUser().getUuid()).isEmpty()) {
            return "redirect:/vehicles/add";
        }

        NewReservationDto newReservationDto = new NewReservationDto();
        newReservationDto.setParkingSpotId(spotId);
        newReservationDto.setParkingSpotLocation(parkingSpotService.getLocationById(spotId));
        List<BankCardDto> bankCardDto = bankCardService.getBankCardsByUsername(userDetails.getUsername());


        model.addAttribute("newReservation", newReservationDto);
        model.addAttribute("vehicles", vehicleService.getUserVehicles(userService.getCurrentUser().getUuid()));
        model.addAttribute("bankingCards", bankCardDto);

        return "reservation-new";
    }

    @PostMapping("/new")
    public String createNewReservation(@ModelAttribute NewReservationDto newReservationDto, @AuthenticationPrincipal UserDetails userDetails) {
        reservationService.createNewReservation(newReservationDto, userDetails.getUsername());
        return "redirect:/reservations";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteReservation(@PathVariable("id") Long id) {
        reservationService.deleteReservation(id);
        return "redirect:/reservations";
    }


}