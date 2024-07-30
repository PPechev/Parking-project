//package bg.softuni.parking.web;
//
//import bg.softuni.parking.model.dto.NewReservationDto;
//import bg.softuni.parking.model.dto.ReservationDto;
//import bg.softuni.parking.model.dto.VehicleDto;
//import bg.softuni.parking.model.entities.ParkingSpot;
//import bg.softuni.parking.model.entities.Reservation;
//import bg.softuni.parking.service.ParkingSpotService;
//import bg.softuni.parking.service.ReservationService;
//import bg.softuni.parking.service.VehicleService;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/reservations")
//public class ReservationController {
//
//    private final ReservationService reservationService;
//    private final VehicleService vehicleService;
//    private final ParkingSpotService parkingSpotService;
//
//    public ReservationController(ReservationService reservationService, VehicleService vehicleService, ParkingSpotService parkingSpotService) {
//        this.reservationService = reservationService;
//        this.vehicleService = vehicleService;
//        this.parkingSpotService = parkingSpotService;
//    }
//
//    @GetMapping
//    public String viewReservations(Model model, @AuthenticationPrincipal UserDetails userDetails) {
//        List<ReservationDto> reservations = reservationService.getUserReservations(userDetails.getUsername());
//        model.addAttribute("reservations", reservations);
//        return "reservations";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String editReservation(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
//        ReservationDto reservation = reservationService.getReservationById(id);
//        List<VehicleDto> vehicles = vehicleService.getUserVehicles(userDetails.getUsername());
//        List<ParkingSpot> availableParkingSpots = parkingSpotService.findAllAvailable();
//        model.addAttribute("reservation", reservation);
//        model.addAttribute("vehicles", vehicles);
//        model.addAttribute("availableParkingSpots", availableParkingSpots);
//        return "reservation-edit";
//    }
//
//    @PostMapping("/update")
//    public String updateReservation(@ModelAttribute ReservationDto reservationDto) {
//        reservationService.updateReservation(reservationDto);
//        return "redirect:/reservations";
//    }
//
//    @GetMapping("/add")
//    public String addReservationForm(Model model, @AuthenticationPrincipal UserDetails userDetails) {
//        model.addAttribute("reservation", new ReservationDto());
//        List<VehicleDto> vehicles = vehicleService.getUserVehicles(userDetails.getUsername());
//        List<ParkingSpot> availableParkingSpots = parkingSpotService.findAllAvailable();
//        model.addAttribute("vehicles", vehicles);
//        model.addAttribute( "availableParkingSpots", availableParkingSpots);
//        return "reservation-adding";
//    }
//
//    @PostMapping("/add")
//    public String addReservation(@ModelAttribute ReservationDto reservationDto, @AuthenticationPrincipal UserDetails userDetails) {
//        reservationService.addReservation(reservationDto, userDetails.getUsername());
//        return "redirect:/reservations";
//    }
//
//    @GetMapping("/all-reservations")
//    public String getAllReservations(Model model) {
//        List<Reservation> reservations = reservationService.findAll();
//        model.addAttribute("reservations", reservations);
//        return "all-reservations";
//    }
//
////    @GetMapping("/new/{parkingSpotLocation}")
////    public String newReservationForm(@PathVariable String parkingSpotLocation, Model model, @AuthenticationPrincipal UserDetails userDetails) {
////        if (!vehicleService.hasVehicles(userDetails.getUsername())) {
////            return "redirect:/vehicles/add";
////        }
////
////        NewReservationDto newReservationDto = new NewReservationDto();
////        newReservationDto.setParkingSpotLocation(parkingSpotLocation);
////
////        model.addAttribute("newReservation", newReservationDto);
////        model.addAttribute("vehicles", vehicleService.getUserVehicles(userDetails.getUsername()));
////        return "reservation-new";
////    }
////
////    @PostMapping("/new")
////    public String createNewReservation(@ModelAttribute NewReservationDto newReservationDto, @AuthenticationPrincipal UserDetails userDetails) {
////        reservationService.createNewReservation(newReservationDto, userDetails.getUsername());
////        return "redirect:/reservations";
////    }
//
//    @GetMapping("/new")
//    public String newReservationForm(@RequestParam("spotId") Long spotId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
//        if (!vehicleService.hasVehicles(userDetails.getUsername())) {
//            return "redirect:/vehicles/add";
//        }
//
//        NewReservationDto newReservationDto = new NewReservationDto();
//        newReservationDto.setParkingSpotId(spotId);
//
//        model.addAttribute("newReservation", newReservationDto);
//        model.addAttribute("vehicles", vehicleService.getUserVehicles(userDetails.getUsername()));
//        return "reservation-new";
//    }
//
//    @PostMapping("/new")
//    public String createNewReservation(@ModelAttribute NewReservationDto newReservationDto, @AuthenticationPrincipal UserDetails userDetails) {
//        reservationService.createNewReservation(newReservationDto, userDetails.getUsername());
//        return "redirect:/reservations";
//    }
//}


package bg.softuni.parking.web;

import bg.softuni.parking.model.dto.NewReservationDto;
import bg.softuni.parking.model.dto.ReservationDto;
import bg.softuni.parking.model.dto.VehicleDto;
import bg.softuni.parking.model.entities.ParkingSpot;
import bg.softuni.parking.model.entities.Reservation;
import bg.softuni.parking.service.ParkingSpotService;
import bg.softuni.parking.service.ReservationService;
import bg.softuni.parking.service.VehicleService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final VehicleService vehicleService;
    private final ParkingSpotService parkingSpotService;

    public ReservationController(ReservationService reservationService, VehicleService vehicleService, ParkingSpotService parkingSpotService) {
        this.reservationService = reservationService;
        this.vehicleService = vehicleService;
        this.parkingSpotService = parkingSpotService;
    }

    @GetMapping
    public String viewReservations(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<ReservationDto> reservations = reservationService.getUserReservations(userDetails.getUsername());
        model.addAttribute("reservations", reservations);
        return "reservations";
    }

    //    @GetMapping("/edit/{id}")
//    public String editReservation(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
//        ReservationDto reservation = reservationService.getReservationById(id);
//        List<VehicleDto> vehicles = vehicleService.getUserVehicles(userDetails.getUsername());
//        List<ParkingSpot> availableParkingSpots = parkingSpotService.findAllAvailable();
//        model.addAttribute("reservation", reservation);
//        model.addAttribute("vehicles", vehicles);
//        model.addAttribute("availableParkingSpots", availableParkingSpots);
//        return "reservation-edit";
//    }
    //vanya
    @GetMapping("/edit/{id}")
    public String editReservation(@PathVariable Long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        ReservationDto reservation = reservationService.getReservationById(id);
        ParkingSpot currentParkingSpot =  parkingSpotService.getParkingSpotById(reservation.getParkingSpotLocation()).get();
        currentParkingSpot.setAvailable(true);
        parkingSpotService.saveParkingSpot(currentParkingSpot);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        String formattedStartTime = reservation.getStartTime().format(formatter);
        String formattedEndTime = reservation.getEndTime().format(formatter);
        reservation.setStartTime(LocalDateTime.parse(formattedStartTime, formatter));
        reservation.setEndTime(LocalDateTime.parse(formattedEndTime, formatter));
        List<VehicleDto> vehicles = vehicleService.getUserVehicles(userDetails.getUsername());
        List<ParkingSpot> availableParkingSpots = parkingSpotService.findAllAvailable();


        model.addAttribute("reservation", reservation);
        model.addAttribute("vehicles", vehicles);
        model.addAttribute("availableParkingSpots", availableParkingSpots);

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
        List<VehicleDto> vehicles = vehicleService.getUserVehicles(userDetails.getUsername());
        List<ParkingSpot> availableParkingSpots = parkingSpotService.findAllAvailable();
        model.addAttribute("vehicles", vehicles);
        model.addAttribute( "availableParkingSpots", availableParkingSpots);
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

//    @GetMapping("/new/{parkingSpotLocation}")
//    public String newReservationForm(@PathVariable String parkingSpotLocation, Model model, @AuthenticationPrincipal UserDetails userDetails) {
//        if (!vehicleService.hasVehicles(userDetails.getUsername())) {
//            return "redirect:/vehicles/add";
//        }
//
//        NewReservationDto newReservationDto = new NewReservationDto();
//        newReservationDto.setParkingSpotLocation(parkingSpotLocation);
//
//        model.addAttribute("newReservation", newReservationDto);
//        model.addAttribute("vehicles", vehicleService.getUserVehicles(userDetails.getUsername()));
//        return "reservation-new";
//    }
//
//    @PostMapping("/new")
//    public String createNewReservation(@ModelAttribute NewReservationDto newReservationDto, @AuthenticationPrincipal UserDetails userDetails) {
//        reservationService.createNewReservation(newReservationDto, userDetails.getUsername());
//        return "redirect:/reservations";
//    }

    @GetMapping("/new")
    public String newReservationForm(@RequestParam("spotId") Long spotId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (!vehicleService.hasVehicles(userDetails.getUsername())) {
            return "redirect:/vehicles/add";
        }

        NewReservationDto newReservationDto = new NewReservationDto();
        newReservationDto.setParkingSpotId(spotId);
        newReservationDto.setParkingSpotLocation(parkingSpotService.getLocationById(spotId));

        model.addAttribute("newReservation", newReservationDto);
        model.addAttribute("vehicles", vehicleService.getUserVehicles(userDetails.getUsername()));
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