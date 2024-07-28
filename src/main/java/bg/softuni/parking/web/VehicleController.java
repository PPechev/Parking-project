package bg.softuni.parking.web;

import bg.softuni.parking.model.dto.VehicleDto;
import bg.softuni.parking.model.entities.Vehicle;
import bg.softuni.parking.service.VehicleService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public String viewVehicles(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<VehicleDto> vehicles = vehicleService.getUserVehicles(userDetails.getUsername());
        model.addAttribute("vehicles", vehicles);
        return "vehicles";
    }

    @GetMapping("/edit/{id}")
    public String editVehicle(@PathVariable Long id, Model model) {
        VehicleDto vehicle = vehicleService.getVehicleById(id);
        model.addAttribute("vehicle", vehicle);
        return "vehicles-edit";
    }

    @PostMapping("/update")
    public String updateVehicle(@ModelAttribute VehicleDto vehicleDto) {
        vehicleService.updateVehicle(vehicleDto);
        return "redirect:/vehicles";
    }

    @GetMapping("/add")
    public String addVehicleForm(Model model) {
        model.addAttribute("vehicle", new VehicleDto());
        return "vehicle-adding";
    }

    @PostMapping("/add")
    public String addVehicle(@ModelAttribute VehicleDto vehicleDto, @AuthenticationPrincipal UserDetails userDetails) {
        vehicleService.addVehicle(vehicleDto, userDetails.getUsername());
        return "redirect:/vehicles";
    }

    @GetMapping("/all-vehicles")
    public String getAllVehicles(Model model) {
        List<Vehicle> vehicles = vehicleService.findAll();
        model.addAttribute("vehicles", vehicles);
        return "all-vehicles";
    }
}
