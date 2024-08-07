//package bg.softuni.parking.web;
//
//import bg.softuni.parking.model.dto.ReservationDto;
//import bg.softuni.parking.model.dto.vehicle.VehicleView;
//import bg.softuni.parking.service.*;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static bg.softuni.parking.Constants.TEST_ID_1;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//class AdminControllerIT {
//
//  @Autowired
//  private MockMvc mockMvc;
//
//  @MockBean
//  private UserService userService;
//
//  @MockBean
//  private ReservationService reservationService;
//
//  @MockBean
//  private ParkingSpotService parkingSpotService;
//
//  @MockBean
//  private VehicleService vehicleService;
//
//  @MockBean
//  private BankCardService bankCardService;
//
//  @Test
//  @WithMockUser(roles = "ADMIN")
//  void testDeleteReservation() throws Exception {
//    // Act & Assert
//    mockMvc.perform(delete("/admin/delete/{id}", TEST_ID_1))
//        .andExpect(status().is3xxRedirection())
//        .andExpect(redirectedUrl("/admin/all-reservations"));
//
//    // Verify
//    verify(reservationService).deleteReservation(TEST_ID_1);
//  }
//
//  @Test
//  @WithMockUser(roles = "ADMIN")
//  void testUpdateReservation() throws Exception {
//    ReservationDto reservationDto = new ReservationDto();
//    reservationDto.setId(TEST_ID_1);
//
//    mockMvc.perform(post("/admin/update")
//            .flashAttr("reservationDto", reservationDto))
//        .andExpect(status().is3xxRedirection())
//        .andExpect(redirectedUrl("/admin/all-reservations"));
//
//    verify(reservationService).updateReservation(reservationDto);
//  }
//
//  @Test
//  @WithMockUser(roles = "ADMIN")
//  void testEditVehicle() throws Exception {
//    VehicleView vehicle = new VehicleView();
//    vehicle.setId(TEST_ID_1);
//
//    when(vehicleService.getVehicleById(TEST_ID_1)).thenReturn(vehicle);
//
//    mockMvc.perform(get("/admin/edit-vehicle/{id}", TEST_ID_1))
//        .andExpect(status().isOk())
//        .andExpect(view().name("vehicles-edit"))
//        .andExpect(model().attribute("vehicle", vehicle));
//
//    verify(vehicleService).getVehicleById(TEST_ID_1);
//  }
//  @Test
//  @WithMockUser(roles = "ADMIN")
//  void testDeleteVehicle() throws Exception {
//    mockMvc.perform(delete("/admin/vehicles/delete/{id}", TEST_ID_1))
//        .andExpect(status().is3xxRedirection())
//        .andExpect(redirectedUrl("/admin/all-vehicles"));
//
//    verify(vehicleService).deleteVehicle(TEST_ID_1);
//  }
//
//  @Test
//  @WithMockUser(roles = "ADMIN")
//  void testAddAdminRole() throws Exception {
//    mockMvc.perform(post("/admin/add-admin-role")
//            .param("userId", TEST_ID_1.toString()))
//        .andExpect(status().is3xxRedirection())
//        .andExpect(redirectedUrl("/admin/all-users"));
//
//    verify(userService).addAdminRole(TEST_ID_1);
//  }
//  @Test
//  @WithMockUser(roles = "ADMIN")
//  void testRemoveAdminRole() throws Exception {
//    mockMvc.perform(post("/admin/remove-admin-role")
//            .param("userId", TEST_ID_1.toString()))
//        .andExpect(status().is3xxRedirection())
//        .andExpect(redirectedUrl("/admin/all-users"));
//
//    verify(userService).removeAdminRole(TEST_ID_1);
//  }
//  @Test
//  @WithMockUser(roles = "ADMIN")
//  void testDeleteUser() throws Exception {
//    mockMvc.perform(post("/admin/delete-user")
//            .param("userId", TEST_ID_1.toString()))
//        .andExpect(status().is3xxRedirection())
//        .andExpect(redirectedUrl("/admin/all-users"));
//
//    verify(userService).deleteUser(TEST_ID_1);
//  }
//
//
//}


//package bg.softuni.parking.web;
//
//import bg.softuni.parking.model.dto.ReservationDto;
//import bg.softuni.parking.model.dto.vehicle.VehicleView;
//import bg.softuni.parking.service.*;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static bg.softuni.parking.Constants.TEST_ID_1;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//class AdminControllerIT {
//
//  @Autowired
//  private MockMvc mockMvc;
//
//  @MockBean
//  private UserService userService;
//
//  @MockBean
//  private ReservationService reservationService;
//
//  @MockBean
//  private ParkingSpotService parkingSpotService;
//
//  @MockBean
//  private VehicleService vehicleService;
//
//  @MockBean
//  private BankCardService bankCardService;
//
//  @Test
//  @WithMockUser(roles = "ADMIN")
//  void testDeleteReservation() throws Exception {
//    // Act & Assert
//    mockMvc.perform(delete("/admin/delete/{id}", TEST_ID_1))
//            .andExpect(status().is3xxRedirection())
//            .andExpect(redirectedUrl("/admin/all-reservations"));
//
//    // Verify
//    verify(reservationService).deleteReservation(TEST_ID_1);
//  }
//
//  @Test
//  @WithMockUser(roles = "ADMIN")
//  void testUpdateReservation() throws Exception {
//    ReservationDto reservationDto = new ReservationDto();
//    reservationDto.setId(TEST_ID_1);
//
//    mockMvc.perform(post("/admin/update")
//                    .flashAttr("reservationDto", reservationDto))
//            .andExpect(status().is3xxRedirection())
//            .andExpect(redirectedUrl("/admin/all-reservations"));
//
//    verify(reservationService).updateReservation(reservationDto);
//  }
//
//  @Test
//  @WithMockUser(roles = "ADMIN")
//  void testEditVehicle() throws Exception {
//    VehicleView vehicle = new VehicleView();
//    vehicle.setId(TEST_ID_1);
//
//    when(vehicleService.getVehicleById(TEST_ID_1)).thenReturn(vehicle);
//
//    mockMvc.perform(get("/admin/edit-vehicle/{id}", TEST_ID_1))
//            .andExpect(status().isOk())
//            .andExpect(view().name("vehicles-edit"))
//            .andExpect(model().attribute("vehicle", vehicle));
//
//    verify(vehicleService).getVehicleById(TEST_ID_1);
//  }
//
//  @Test
//  @WithMockUser(roles = "ADMIN")
//  void testDeleteVehicle() throws Exception {
//    mockMvc.perform(delete("/admin/vehicles/delete/{id}", TEST_ID_1))
//            .andExpect(status().is3xxRedirection())
//            .andExpect(redirectedUrl("/admin/all-vehicles"));
//
//    verify(vehicleService).deleteVehicle(TEST_ID_1);
//  }
//
//  @Test
//  @WithMockUser(roles = "ADMIN")
//  void testAddAdminRole() throws Exception {
//    mockMvc.perform(post("/admin/add-admin-role")
//                    .param("userId", TEST_ID_1.toString()))
//            .andExpect(status().is3xxRedirection())
//            .andExpect(redirectedUrl("/admin/all-users"));
//
//    verify(userService).addAdminRole(TEST_ID_1);
//  }
//
//  @Test
//  @WithMockUser(roles = "ADMIN")
//  void testRemoveAdminRole() throws Exception {
//    mockMvc.perform(post("/admin/remove-admin-role")
//                    .param("userId", TEST_ID_1.toString()))
//            .andExpect(status().is3xxRedirection())
//            .andExpect(redirectedUrl("/admin/all-users"));
//
//    verify(userService).removeAdminRole(TEST_ID_1);
//  }
//
//  @Test
//  @WithMockUser(roles = "ADMIN")
//  void testDeleteUser() throws Exception {
//    mockMvc.perform(post("/admin/delete-user")
//                    .param("userId", TEST_ID_1.toString()))
//            .andExpect(status().is3xxRedirection())
//            .andExpect(redirectedUrl("/admin/all-users"));
//
//    verify(userService).deleteUser(TEST_ID_1);
//  }
//}


package bg.softuni.parking.web;

import bg.softuni.parking.exception.ReservationNotFoundException;
import bg.softuni.parking.model.dto.BankCardDto;
import bg.softuni.parking.model.dto.ReservationDto;
import bg.softuni.parking.model.dto.UserWithRolesDto;
import bg.softuni.parking.model.dto.reservationAdminView.ReservationAdminView;
import bg.softuni.parking.model.dto.vehicle.VehicleView;
import bg.softuni.parking.model.dto.vehicle.VehicleViewAdmin;
import bg.softuni.parking.model.entities.ParkingSpot;
import bg.softuni.parking.model.entities.User;
import bg.softuni.parking.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.ArrayList;

import static bg.softuni.parking.Constants.TEST_ID_1;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private ParkingSpotService parkingSpotService;

    @MockBean
    private VehicleService vehicleService;

    @MockBean
    private BankCardService bankCardService;

//    @Test
//    @WithMockUser(roles = "ADMIN")
//    void testGetAllReservations() throws Exception {
//        List<ReservationAdminView> reservations = new ArrayList<>();
//        reservations.add(new ReservationAdminView().setId(TEST_ID_1));
//
//        when(reservationService.findAllForAdmin()).thenReturn(reservations);
//
//        mockMvc.perform(get("/admin/all-reservations"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("all-reservations"))
//                .andExpect(model().attribute("reservations", reservations));
//
//        verify(reservationService).findAllForAdmin();
//    }

//    @Test
//    @WithMockUser(roles = "ADMIN")
//    void testEditReservation() throws Exception {
//        ReservationDto reservation = new ReservationDto();
//        reservation.setId(TEST_ID_1);
//        reservation.setParkingSpotLocation("Spot 1");
//
//        List<ParkingSpot> availableSpots = new ArrayList<>();
//        availableSpots.add(new ParkingSpot().setLocation("Spot 2"));
//        List<BankCardDto> bankCards = new ArrayList<>();
//        bankCards.add(new BankCardDto());
//
//        when(reservationService.getFormattedReservationById(TEST_ID_1)).thenReturn(reservation);
//        when(parkingSpotService.findAllAvailable()).thenReturn(availableSpots);
//        when(bankCardService.getBankCardsByUsername(anyString())).thenReturn(bankCards);
//        when(vehicleService.getUserVehicles(anyString())).thenReturn(List.of(new VehicleView()));
//
//        mockMvc.perform(get("/admin/edit/{id}", TEST_ID_1))
//                .andExpect(status().isOk())
//                .andExpect(view().name("reservation-edit"))
//                .andExpect(model().attribute("reservation", reservation))
//                .andExpect(model().attribute("availableParkingSpots", availableSpots))
//                .andExpect(model().attribute("vehicles", hasSize(1)))
//                .andExpect(model().attribute("bankingCards", bankCards));
//
//        verify(reservationService).getFormattedReservationById(TEST_ID_1);
//        verify(parkingSpotService).findAllAvailable();
//        verify(bankCardService).getBankCardsByUsername(anyString());
//        verify(vehicleService).getUserVehicles(anyString());
//    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAllVehicles() throws Exception {
        VehicleViewAdmin vehicle = new VehicleViewAdmin();
        vehicle.setId(TEST_ID_1);
        vehicle.setOwner("User");

        List<VehicleViewAdmin> vehicles = new ArrayList<>();
        vehicles.add(vehicle);

        when(vehicleService.findAll()).thenReturn(vehicles);
        when(userService.findByUuid(anyString())).thenReturn(new User().setUsername("User"));

        mockMvc.perform(get("/admin/all-vehicles"))
                .andExpect(status().isOk())
                .andExpect(view().name("all-vehicles"))
                .andExpect(model().attribute("vehicles", hasSize(1)))
                .andExpect(model().attribute("vehicles", hasItem(hasProperty("owner", is("User")))));

        verify(vehicleService).findAll();
        verify(userService).findByUuid(anyString());
    }

//    @Test
//    @WithMockUser(roles = "ADMIN")
//    void testViewAllUsers() throws Exception {
//        UserWithRolesDto user = new UserWithRolesDto();
//        user.setUsername("User");
//
//        List<UserWithRolesDto> users = new ArrayList<>();
//        users.add(user);
//
//        when(userService.getAllUsersWithRoles()).thenReturn(users);
//
//        mockMvc.perform(get("/admin/all-users"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("all-users"))
//                .andExpect(model().attribute("users", users));
//
//        verify(userService).getAllUsersWithRoles();
//    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteReservation() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/admin/delete/{id}", TEST_ID_1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/all-reservations"));

        // Verify
        verify(reservationService).deleteReservation(TEST_ID_1);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateReservation() throws Exception {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(TEST_ID_1);

        mockMvc.perform(post("/admin/update")
                        .flashAttr("reservationDto", reservationDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/all-reservations"));

        verify(reservationService).updateReservation(reservationDto);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testEditVehicle() throws Exception {
        VehicleView vehicle = new VehicleView();
        vehicle.setId(TEST_ID_1);

        when(vehicleService.getVehicleById(TEST_ID_1)).thenReturn(vehicle);

        mockMvc.perform(get("/admin/edit-vehicle/{id}", TEST_ID_1))
                .andExpect(status().isOk())
                .andExpect(view().name("vehicles-edit"))
                .andExpect(model().attribute("vehicle", vehicle));

        verify(vehicleService).getVehicleById(TEST_ID_1);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteVehicle() throws Exception {
        mockMvc.perform(delete("/admin/vehicles/delete/{id}", TEST_ID_1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/all-vehicles"));

        verify(vehicleService).deleteVehicle(TEST_ID_1);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAddAdminRole() throws Exception {
        mockMvc.perform(post("/admin/add-admin-role")
                        .param("userId", TEST_ID_1.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/all-users"));

        verify(userService).addAdminRole(TEST_ID_1);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testRemoveAdminRole() throws Exception {
        mockMvc.perform(post("/admin/remove-admin-role")
                        .param("userId", TEST_ID_1.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/all-users"));

        verify(userService).removeAdminRole(TEST_ID_1);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testDeleteUser() throws Exception {
        mockMvc.perform(post("/admin/delete-user")
                        .param("userId", TEST_ID_1.toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/all-users"));

        verify(userService).deleteUser(TEST_ID_1);
    }

//    @Test
//    @WithMockUser(roles = "ADMIN")
//    void testEditReservation_NotFound() throws Exception {
//        when(reservationService.getFormattedReservationById(TEST_ID_1)).thenThrow(new ReservationNotFoundException("Reservation not found"));
//
//        mockMvc.perform(get("/admin/edit/{id}", TEST_ID_1))
//                .andExpect(status().isNotFound());
//
//        verify(reservationService).getFormattedReservationById(TEST_ID_1);
//    }
//
//    @Test
//    @WithMockUser(roles = "ADMIN")
//    void testDeleteReservation_NotFound() throws Exception {
//        doThrow(new ReservationNotFoundException("Reservation not found")).when(reservationService).deleteReservation(TEST_ID_1);
//
//        mockMvc.perform(delete("/admin/delete/{id}", TEST_ID_1))
//                .andExpect(status().isNotFound());
//
//        verify(reservationService).deleteReservation(TEST_ID_1);
//    }
}
