//


package bg.softuni.parking.web;


import bg.softuni.parking.model.dto.vehicle.VehicleView;
import bg.softuni.parking.service.UserService;
import bg.softuni.parking.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

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



    @Test
    @WithMockUser(username = "user")
    void testDeleteVehicle() throws Exception {
        mockMvc.perform(delete("/vehicles/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));

        verify(vehicleService, times(1)).deleteVehicle(1L);
    }


}
