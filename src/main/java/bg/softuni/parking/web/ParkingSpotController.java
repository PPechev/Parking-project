package bg.softuni.parking.web;

import bg.softuni.parking.Service.ParkingSpotService;
import bg.softuni.parking.model.entities.ParkingSpot;
import bg.softuni.parking.repository.ParkingSpotRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ParkingSpotController {

    private final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @GetMapping("/parking-spots")
        public String listParkingSpots(Model model) {
            List<ParkingSpot> parkingSpots = parkingSpotService.findAll();
            model.addAttribute("parkingSpots", parkingSpots);
            return "parking-spots";
        }
    }

