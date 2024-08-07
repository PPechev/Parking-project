package bg.softuni.parking.web;

import bg.softuni.parking.model.entities.ParkingSpot;
import bg.softuni.parking.model.entities.User;
import bg.softuni.parking.service.ParkingSpotService;
import bg.softuni.parking.service.UserService;
import bg.softuni.parking.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static bg.softuni.parking.Constants.USERNAME;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ParkingSpotControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingSpotService parkingSpotService;

    @MockBean
    private UserService userService;

    @MockBean
    private VehicleService vehicleService;



    @Test
    @WithMockUser(username = USERNAME)
    void testGetParkingSpots_LoggedInNoVehicles() throws Exception {
        User user = new User();
        user.setUsername(USERNAME);
        user.setUuid("test-uuid");

        ParkingSpot parkingSpot = new ParkingSpot();
        List<ParkingSpot> parkingSpots = List.of(parkingSpot);

        when(userService.findByUsername(USERNAME)).thenReturn(Optional.of(user));
        when(vehicleService.getUserVehicles(user.getUuid())).thenReturn(Collections.emptyList());
        when(parkingSpotService.findAll()).thenReturn(parkingSpots);

        mockMvc.perform(get("/parking-spots"))
                .andExpect(status().isOk())
                .andExpect(view().name("parking-spots"))
                .andExpect(model().attribute("parkingSpots", parkingSpots))
                .andExpect(model().attribute("isLoggedIn", true))
                .andExpect(model().attribute("hasVehicles", false));

        verify(userService).findByUsername(USERNAME);
        verify(vehicleService).getUserVehicles(user.getUuid());
        verify(parkingSpotService).findAll();
    }

    @Test
    void testGetParkingSpots_NotLoggedIn() throws Exception {
        ParkingSpot parkingSpot = new ParkingSpot();
        List<ParkingSpot> parkingSpots = List.of(parkingSpot);

        when(parkingSpotService.findAll()).thenReturn(parkingSpots);

        mockMvc.perform(get("/parking-spots"))
                .andExpect(status().isOk())
                .andExpect(view().name("parking-spots"))
                .andExpect(model().attribute("parkingSpots", parkingSpots))
                .andExpect(model().attribute("isLoggedIn", false))
                .andExpect(model().attribute("hasVehicles", false));

        verify(parkingSpotService).findAll();
    }
}
