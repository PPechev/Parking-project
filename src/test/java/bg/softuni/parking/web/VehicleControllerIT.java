//package bg.softuni.parking.web;
//
//import bg.softuni.parking.exception.VehicleAlreadyExists;
//import bg.softuni.parking.exception.VehicleNotFoundException;
//import bg.softuni.parking.model.dto.vehicle.VehicleCreateDto;
//import bg.softuni.parking.model.dto.vehicle.VehicleEditDto;
//import bg.softuni.parking.model.dto.vehicle.VehicleView;
//import bg.softuni.parking.service.UserService;
//import bg.softuni.parking.service.VehicleService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.UUID;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith({SpringExtension.class, MockitoExtension.class})
//@SpringBootTest
//@AutoConfigureMockMvc
//class VehicleControllerIT {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private VehicleService vehicleService;
//
//    @MockBean
//    private UserService userService;
//
//    @Test
//    @WithMockUser(username = "user")
//    void testViewVehicles() throws Exception {
//        UUID userUuid = UUID.randomUUID();
//        VehicleView vehicle = new VehicleView();
//        vehicle.setId(1L);
//        List<VehicleView> vehicles = Collections.singletonList(vehicle);
//
//        when(userService.getCurrentUser().getUuid()).thenReturn(String.valueOf(userUuid));
//        when(vehicleService.getUserVehicles(userUuid)).thenReturn(vehicles);
//
//        mockMvc.perform(get("/vehicles"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("vehicles"))
//                .andExpect(model().attribute("vehicles", vehicles));
//
//        verify(vehicleService, times(1)).getUserVehicles(userUuid);
//    }
//
//    @Test
//    @WithMockUser(username = "user")
//    void testEditVehicle() throws Exception {
//        VehicleView vehicle = new VehicleView();
//        vehicle.setId(1L);
//
//        when(vehicleService.getVehicleById(1L)).thenReturn(vehicle);
//
//        mockMvc.perform(get("/vehicles/edit/1"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("vehicles-edit"))
//                .andExpect(model().attribute("vehicle", vehicle));
//
//        verify(vehicleService, times(1)).getVehicleById(1L);
//    }
//
//    @Test
//    @WithMockUser(username = "user")
//    void testAddVehicleForm() throws Exception {
//        mockMvc.perform(get("/vehicles/add"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("vehicle-adding"))
//                .andExpect(model().attributeExists("vehicleCreateDto"));
//    }
//
//    @Test
//    @WithMockUser(username = "user")
//    void testAddVehicle() throws Exception {
//        VehicleCreateDto vehicleCreateDto = new VehicleCreateDto();
//        vehicleCreateDto.setLicensePlate("ABC123");
//
//        mockMvc.perform(post("/vehicles/add")
//                        .flashAttr("vehicleCreateDto", vehicleCreateDto))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/profile"));
//
//        verify(vehicleService, times(1)).addVehicle(any(VehicleCreateDto.class), any(UUID.class));
//    }
//
//    @Test
//    @WithMockUser(username = "user")
//    void testDeleteVehicle() throws Exception {
//        mockMvc.perform(delete("/vehicles/delete/1"))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/profile"));
//
//        verify(vehicleService, times(1)).deleteVehicle(1L);
//    }
//
//    @Test
//    @WithMockUser(username = "user")
//    void testUpdateVehicle() throws Exception {
//        VehicleEditDto vehicleEditDto = new VehicleEditDto();
//        vehicleEditDto.setLicensePlate("XYZ789");
//
//        mockMvc.perform(post("/vehicles/edit/1")
//                        .flashAttr("vehicleEditDto", vehicleEditDto))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/vehicles"));
//
//        verify(vehicleService, times(1)).updateVehicle(1L, vehicleEditDto);
//    }
//}


package bg.softuni.parking.web;

import bg.softuni.parking.exception.VehicleAlreadyExists;
import bg.softuni.parking.exception.VehicleNotFoundException;
import bg.softuni.parking.model.dto.vehicle.VehicleCreateDto;
import bg.softuni.parking.model.dto.vehicle.VehicleEditDto;
import bg.softuni.parking.model.dto.vehicle.VehicleView;
import bg.softuni.parking.service.UserService;
import bg.softuni.parking.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class VehicleControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VehicleService vehicleService;

    @MockBean
    private UserService userService;

//    @Test
//    @WithMockUser(username = "user")
//    void testViewVehicles() throws Exception {
//        String userUuid = UUID.randomUUID().toString();
//        VehicleView vehicle = new VehicleView();
//        vehicle.setId(1L);
//        List<VehicleView> vehicles = Collections.singletonList(vehicle);
//
//        when(userService.getCurrentUser().getUuid()).thenReturn(userUuid);
//        when(vehicleService.getUserVehicles(userUuid)).thenReturn(vehicles);
//
//        mockMvc.perform(get("/vehicles"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("vehicles"))
//                .andExpect(model().attribute("vehicles", vehicles));
//
//        verify(vehicleService, times(1)).getUserVehicles(userUuid);
//    }

    @Test
    @WithMockUser(username = "user")
    void testEditVehicle() throws Exception {
        VehicleView vehicle = new VehicleView();
        vehicle.setId(1L);

        when(vehicleService.getVehicleById(1L)).thenReturn(vehicle);

        mockMvc.perform(get("/vehicles/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("vehicles-edit"))
                .andExpect(model().attribute("vehicle", vehicle));

        verify(vehicleService, times(1)).getVehicleById(1L);
    }

    @Test
    @WithMockUser(username = "user")
    void testAddVehicleForm() throws Exception {
        mockMvc.perform(get("/vehicles/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("vehicle-adding"))
                .andExpect(model().attributeExists("vehicleCreateDto"));
    }

//    @Test
//    @WithMockUser(username = "user")
//    void testAddVehicle() throws Exception {
//        VehicleCreateDto vehicleCreateDto = new VehicleCreateDto();
//        vehicleCreateDto.setLicensePlate("ABC123");
//
//        mockMvc.perform(post("/vehicles/add")
//                        .flashAttr("vehicleCreateDto", vehicleCreateDto))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/profile"));
//
//        verify(vehicleService, times(1)).addVehicle(any(VehicleCreateDto.class), anyString());
//    }

    @Test
    @WithMockUser(username = "user")
    void testDeleteVehicle() throws Exception {
        mockMvc.perform(delete("/vehicles/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));

        verify(vehicleService, times(1)).deleteVehicle(1L);
    }

//    @Test
//    @WithMockUser(username = "user")
//    void testUpdateVehicle() throws Exception {
//        VehicleEditDto vehicleEditDto = new VehicleEditDto();
//        vehicleEditDto.setLicensePlate("XYZ789");
//
//        mockMvc.perform(post("/vehicles/edit/1")
//                        .flashAttr("vehicleEditDto", vehicleEditDto))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/vehicles"));
//
//        verify(vehicleService, times(1)).updateVehicle(1L, vehicleEditDto);
//    }
}
