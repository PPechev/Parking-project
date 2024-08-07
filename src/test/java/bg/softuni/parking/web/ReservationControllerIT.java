package bg.softuni.parking.web;

import bg.softuni.parking.model.dto.NewReservationDto;
import bg.softuni.parking.model.dto.ReservationDto;
import bg.softuni.parking.model.user.ParkingUserDetails;
import bg.softuni.parking.service.*;
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
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import static bg.softuni.parking.Constants.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private VehicleService vehicleService;

    @MockBean
    private ParkingSpotService parkingSpotService;

    @MockBean
    private UserService userService;

    @MockBean
    private BankCardService bankCardService;

    @Test
    @WithMockUser(username = "testUser")
    void testViewReservations() throws Exception {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(TEST_ID_1);
        reservationDto.setStartTime(LocalDateTime.now().plusHours(1));
        reservationDto.setEndTime(LocalDateTime.now().plusHours(2));

        when(userService.getCurrentUser()).thenReturn(new ParkingUserDetails("testUser", "password", Collections.emptyList(), "First", "Last", "12345", "testUser@example.com", "uuid"));
        when(reservationService.getUserReservations("testUser")).thenReturn(List.of(reservationDto));

        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk())
                .andExpect(view().name("reservations"))
                .andExpect(model().attributeExists("reservations"));

        verify(reservationService).getUserReservations("testUser");
    }

    @Test
    @WithMockUser(username = "testUser")
    void testEditReservation() throws Exception {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(TEST_ID_1);
        reservationDto.setParkingSpotLocation("Location");
        reservationDto.setStartTime(LocalDateTime.now().plusHours(1));
        reservationDto.setEndTime(LocalDateTime.now().plusHours(2));

        when(userService.getCurrentUser()).thenReturn(new ParkingUserDetails("testUser", "password", Collections.emptyList(), "First", "Last", "12345", "testUser@example.com", "uuid"));
        when(reservationService.getFormattedReservationById(TEST_ID_1)).thenReturn(reservationDto);
        when(parkingSpotService.findAllAvailable()).thenReturn(Collections.emptyList());
        when(vehicleService.getUserVehicles("uuid")).thenReturn(Collections.emptyList());
        when(bankCardService.getBankCardsByUsername("testUser")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/reservations/edit/{id}", TEST_ID_1))
                .andExpect(status().isOk())
                .andExpect(view().name("reservation-edit"))
                .andExpect(model().attributeExists("reservation"))
                .andExpect(model().attributeExists("vehicles"))
                .andExpect(model().attributeExists("availableParkingSpots"))
                .andExpect(model().attributeExists("bankingCards"));

        verify(reservationService).getFormattedReservationById(TEST_ID_1);
    }

    @Test
    @WithMockUser(username = "testUser")
    void testAddReservationForm() throws Exception {
        when(userService.getCurrentUser()).thenReturn(new ParkingUserDetails("testUser", "password", Collections.emptyList(), "First", "Last", "12345", "testUser@example.com", "uuid"));
        when(vehicleService.getUserVehicles("uuid")).thenReturn(Collections.emptyList());
        when(parkingSpotService.findAllAvailable()).thenReturn(Collections.emptyList());
        when(bankCardService.getBankCardsByUsername("testUser")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/reservations/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("reservation-adding"))
                .andExpect(model().attributeExists("reservation"))
                .andExpect(model().attributeExists("vehicles"))
                .andExpect(model().attributeExists("availableParkingSpots"))
                .andExpect(model().attributeExists("bankingCards"));
    }

    @Test
    @WithMockUser(username = "testUser")
    void testAddReservation() throws Exception {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(TEST_ID_1);

        mockMvc.perform(post("/reservations/add")
                        .flashAttr("reservationDto", reservationDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reservations"));

        verify(reservationService).addReservation(any(ReservationDto.class), eq("testUser"));
    }

    @Test
    @WithMockUser(username = "testUser")
    void testCreateNewReservation() throws Exception {
        NewReservationDto newReservationDto = new NewReservationDto();
        newReservationDto.setParkingSpotId(TEST_ID_1);

        mockMvc.perform(post("/reservations/new")
                        .flashAttr("newReservation", newReservationDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reservations"));

        verify(reservationService).createNewReservation(any(NewReservationDto.class), eq("testUser"));
    }

    @Test
    @WithMockUser(username = "testUser")
    void testDeleteReservation() throws Exception {
        mockMvc.perform(delete("/reservations/delete/{id}", TEST_ID_1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/reservations"));

        verify(reservationService).deleteReservation(TEST_ID_1);
    }
}
