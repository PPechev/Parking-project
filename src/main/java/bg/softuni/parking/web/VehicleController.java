//package bg.softuni.parking.web;
//
//import bg.softuni.parking.Service.VehicleService;
//import bg.softuni.parking.model.entities.Vehicle;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//
//@Controller
//public class VehicleController {
//
//
//    private final VehicleService vehicleService;
//
//    public VehicleController(VehicleService vehicleService) {
//        this.vehicleService = vehicleService;
//    }
//
//    @GetMapping("/vehicles")
//    public String vehicles(Model model) {
//        List<Vehicle> vehicles = vehicleService.findAll();
//        model.addAttribute("vehicles", vehicles);
//        return "vehicles";
//    }
//}


package bg.softuni.parking.web;

import bg.softuni.parking.Service.VehicleService;
import bg.softuni.parking.model.entities.Vehicle;
import bg.softuni.parking.model.user.ParkingUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping("/vehicles")
    public String getUserVehicles(@RequestParam String username, Model model) {
        List<Vehicle> vehicles = vehicleService.findVehiclesByUsername(username);
        model.addAttribute("vehicles", vehicles);
        return "vehicles";
    }


    @GetMapping("/all-vehicles")
    public String getAllVehicles(Model model) {
        List<Vehicle> vehicles = vehicleService.findAll();
        model.addAttribute("vehicles", vehicles);
        return "all-vehicles";
    }

}
