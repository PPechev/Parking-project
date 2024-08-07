package bg.softuni.parking.web;

import bg.softuni.parking.exception.VehicleAlreadyExists;
import bg.softuni.parking.exception.VehicleNotFoundException;
import bg.softuni.parking.model.dto.vehicle.VehicleCreateDto;
import bg.softuni.parking.model.dto.vehicle.VehicleEditDto;
import bg.softuni.parking.model.dto.vehicle.VehicleView;
import bg.softuni.parking.model.entities.Reservation;
import bg.softuni.parking.service.ReservationService;
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
    private final ReservationService reservationService;
    public VehicleController(VehicleService vehicleService, UserService userService, ReservationService reservationService) {
        this.vehicleService = vehicleService;
        this.userService = userService;
        this.reservationService = reservationService;
    }




            @GetMapping
            public String viewVehicles(Model model) {
                try {
                    List<VehicleView> vehicles = vehicleService.getUserVehicles(userService.getCurrentUser().getUuid());
                    model.addAttribute("vehicles", vehicles);
                } catch (VehicleNotFoundException e) {
                    model.addAttribute("errorMessage", e.getMessage());
                    return "error";
                }
                return "vehicles";
            }




            @GetMapping("/edit/{id}")
        public String editVehicle(@PathVariable Long id, Model model) {
          try {
            VehicleView vehicle = vehicleService.getVehicleById(id);
            model.addAttribute("vehicle", vehicle);
          } catch (VehicleNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
          }
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
          if (!model.containsAttribute("vehicleCreateDto")) {
            model.addAttribute("vehicleCreateDto", new VehicleCreateDto());
          }
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

            try {
              vehicleService.addVehicle(vehicleCreateDto, userService.getCurrentUser().getUuid());
            } catch (VehicleAlreadyExists e) {
              redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
              redirectAttributes.addFlashAttribute("vehicleCreateDto", vehicleCreateDto);
              return "redirect:/vehicles/add";
            }
            return "redirect:/profile";
          }



             @DeleteMapping("/delete/{id}")
        public String deleteVehicle(@PathVariable Long id, RedirectAttributes redirectAttributes) {
            try {
                List<Reservation> allByVehicleId = reservationService.findAllByVehicleId(id);
                allByVehicleId.forEach(reservation -> {
                    reservationService.deleteReservation(reservation.getId());
                });

                vehicleService.deleteVehicle(id);

            } catch (VehicleNotFoundException e) {
                redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
                return "redirect:/vehicles";
            }
            return "redirect:/profile";
        }
}
