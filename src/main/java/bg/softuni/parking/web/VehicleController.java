package bg.softuni.parking.web;

import bg.softuni.parking.model.dto.VehicleDto;
import bg.softuni.parking.model.dto.vehicle.VehicleCreateDto;
import bg.softuni.parking.model.dto.vehicle.VehicleEditDto;
import bg.softuni.parking.model.dto.vehicle.VehicleView;
import bg.softuni.parking.service.UserService;
import bg.softuni.parking.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
    private final UserService userService;

    public VehicleController(VehicleService vehicleService, UserService userService) {
        this.vehicleService = vehicleService;
        this.userService = userService;
    }
    // todo
//  @GetMapping("/all-vehicles")
//  public String getAllVehicles(Model model) {
//    List<VehicleView> vehicles = vehicleService.findAll();
//    model.addAttribute("vehicles", vehicles);
//    return "all-vehicles";
//  }

    @GetMapping
    public String viewVehicles(Model model) {
        List<VehicleView> vehicles = vehicleService.getUserVehicles(userService.getCurrentUser().getUuid());

        model.addAttribute("vehicles", vehicles);
        return "vehicles";
    }

    @GetMapping("/edit/{id}")
    public String editVehicle(@PathVariable Long id, Model model) {
        VehicleView vehicle = vehicleService.getVehicleById(id);
        model.addAttribute("vehicle", vehicle);
        return "vehicles-edit";
    }

    @PostMapping("/edit/{id}")
    public String updateVehicle(
            @PathVariable Long id,
            @Valid VehicleEditDto vehicleEditDto,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("vehicleEditDto", vehicleEditDto);
            model.addAttribute("org.springframework.validation.BindingResult.vehicleEditDto", bindingResult);
            return "vehicles-edit";
        }
        vehicleService.updateVehicle(id, vehicleEditDto);
        return "redirect:/vehicles";
    }


    @GetMapping("/add")
    public String addVehicleForm(Model model) {
        model.addAttribute("vehicle", new VehicleDto());
        return "vehicle-adding";
    }

    @PostMapping("/add")
    public String addVehicle(
            @Valid VehicleCreateDto vehicleCreateDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("vehicleCreateDto", vehicleCreateDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.vehicleCreateDto", bindingResult);
            return "redirect:/vehicles/add";
        }
        vehicleService.addVehicle(vehicleCreateDto, userService.getCurrentUser().getUuid());
        return "redirect:/profile";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable Long id) {
        this.vehicleService.deleteVehicle(id);
        return "redirect:/profile";
    }
}
