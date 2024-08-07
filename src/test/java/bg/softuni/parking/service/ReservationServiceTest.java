
package bg.softuni.parking.service;

import bg.softuni.parking.Constants;
import bg.softuni.parking.exception.ParkingSpotNotFoundException;
import bg.softuni.parking.exception.ReservationNotFoundException;
import bg.softuni.parking.exception.UsernameNotFoundException;
import bg.softuni.parking.model.dto.NewReservationDto;
import bg.softuni.parking.model.dto.ReservationDto;
import bg.softuni.parking.model.dto.reservationAdminView.ReservationAdminView;
import bg.softuni.parking.model.dto.vehicle.VehicleView;
import bg.softuni.parking.model.entities.ParkingSpot;
import bg.softuni.parking.model.entities.Reservation;
import bg.softuni.parking.model.entities.Role;
import bg.softuni.parking.model.entities.User;
import bg.softuni.parking.model.enums.UserRoleEnum;
import bg.softuni.parking.repository.ReservationRepository;
import bg.softuni.parking.utils.DateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static bg.softuni.parking.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

  @Mock
  private ReservationRepository reservationRepository;

  @Mock
  private ParkingSpotService parkingSpotService;

  @Mock
  private UserService userService;

  @Mock
  private VehicleService vehicleService;

  @InjectMocks
  private ReservationService reservationService;

  @Test
  void testGetUserReservations_UserExists() {
    // Arrange
    Role userRole = new Role();
    userRole.setName(UserRoleEnum.USER);

    User user = new User();
    user.setUsername(Constants.USERNAME);
    user.setRoles(Set.of(userRole));

    ParkingSpot parkingSpot = new ParkingSpot();
    parkingSpot.setLocation(PARKING_SPOT_LOCATION);

    Reservation reservation = new Reservation();
    reservation.setId(TEST_ID_1);
    reservation.setParkingSpot(parkingSpot);
    reservation.setStartTime(LocalDateTime.now());
    reservation.setEndTime(LocalDateTime.now().plusHours(1));
    reservation.setUser(user);
    reservation.setVehicleId(TEST_ID_1);

    VehicleView vehicleView = new VehicleView();
    vehicleView.setId(TEST_ID_1);
    vehicleView.setLicensePlate(VEHICLE_LICENSE_PLATE);

    when(userService.findByUsername(Constants.USERNAME)).thenReturn(Optional.of(user));
    when(reservationRepository.findByUser(user)).thenReturn(List.of(reservation));
    when(vehicleService.getVehicleById(TEST_ID_1)).thenReturn(vehicleView);

    // Act
    List<ReservationDto> result = reservationService.getUserReservations(Constants.USERNAME);

    // Assert
    assertEquals(1, result.size());
    assertEquals(TEST_ID_1, result.get(0).getId());
    assertEquals(PARKING_SPOT_LOCATION, result.get(0).getParkingSpotLocation());
    assertEquals(TEST_ID_1, result.get(0).getVehicleId());
    assertEquals(VEHICLE_LICENSE_PLATE, result.get(0).getVehicleLicensePlate());
  }

  @Test
  void testGetReservationById_ReservationExists() {
    // Arrange
    ParkingSpot parkingSpot = new ParkingSpot();
    parkingSpot.setLocation(PARKING_SPOT_LOCATION);

    Reservation reservation = new Reservation();
    reservation.setId(TEST_ID_1);
    reservation.setParkingSpot(parkingSpot);
    reservation.setStartTime(LocalDateTime.now());
    reservation.setEndTime(LocalDateTime.now().plusHours(1));
    reservation.setVehicleId(TEST_ID_1);

    VehicleView vehicleView = new VehicleView();
    vehicleView.setId(TEST_ID_1);
    vehicleView.setLicensePlate(VEHICLE_LICENSE_PLATE);

    when(reservationRepository.findById(TEST_ID_1)).thenReturn(Optional.of(reservation));
    when(vehicleService.getVehicleById(TEST_ID_1)).thenReturn(vehicleView);

    // Act
    ReservationDto result = reservationService.getReservationById(TEST_ID_1);

    // Assert
    assertEquals(TEST_ID_1, result.getId());
    assertEquals(PARKING_SPOT_LOCATION, result.getParkingSpotLocation());
    assertEquals(TEST_ID_1, result.getVehicleId());
    assertEquals(VEHICLE_LICENSE_PLATE, result.getVehicleLicensePlate());
  }

  @Test
  void testGetReservationById_ReservationNotFound() {
    // Arrange
    when(reservationRepository.findById(TEST_ID_1)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(ReservationNotFoundException.class, () -> {
      reservationService.getReservationById(TEST_ID_1);
    });
  }

  @Test
  void testUpdateReservation_ReservationExists() {
    // Arrange
    ParkingSpot parkingSpot = new ParkingSpot();
    parkingSpot.setLocation(PARKING_SPOT_LOCATION);

    Reservation reservation = new Reservation();
    reservation.setId(TEST_ID_1);
    reservation.setParkingSpot(parkingSpot);
    reservation.setStartTime(Constants.TEST_DATE_TIME_1);
    reservation.setEndTime(Constants.TEST_DATE_TIME_2);
    reservation.setVehicleId(TEST_ID_1);

    VehicleView vehicleView = new VehicleView();
    vehicleView.setId(TEST_ID_1);

    ReservationDto reservationDto = new ReservationDto();
    reservationDto.setId(TEST_ID_1);
    reservationDto.setParkingSpotLocation(PARKING_SPOT_LOCATION);
    reservationDto.setStartTime(Constants.TEST_DATE_TIME_1);
    reservationDto.setEndTime(Constants.TEST_DATE_TIME_2);
    reservationDto.setVehicleId(TEST_ID_1);

    when(reservationRepository.findById(TEST_ID_1)).thenReturn(Optional.of(reservation));
    when(parkingSpotService.findByLocation(PARKING_SPOT_LOCATION)).thenReturn(Optional.of(parkingSpot));
    when(vehicleService.getVehicleById(TEST_ID_1)).thenReturn(vehicleView);

    // Act
    reservationService.updateReservation(reservationDto);

    // Assert
    verify(reservationRepository).save(reservation);
    assertEquals(PARKING_SPOT_LOCATION, reservation.getParkingSpot().getLocation());
    assertEquals(Constants.TEST_DATE_TIME_1, reservation.getStartTime());
    assertEquals(Constants.TEST_DATE_TIME_2, reservation.getEndTime());
    assertEquals(TEST_ID_1, reservation.getVehicleId());
    assertFalse(reservation.getParkingSpot().isAvailable());
  }

  @Test
  void testUpdateReservation_ReservationNotFound() {
    // Arrange
    ReservationDto reservationDto = new ReservationDto();
    reservationDto.setId(TEST_ID_1);

    when(reservationRepository.findById(TEST_ID_1)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(ReservationNotFoundException.class, () -> {
      reservationService.updateReservation(reservationDto);
    });
  }

  @Test
  void testUpdateReservation_ParkingSpotNotFound() {
    // Arrange
    ParkingSpot parkingSpot = new ParkingSpot();
    parkingSpot.setLocation(PARKING_SPOT_LOCATION);

    Reservation reservation = new Reservation();
    reservation.setId(TEST_ID_1);
    reservation.setParkingSpot(parkingSpot);
    reservation.setStartTime(LocalDateTime.now().minusHours(1));
    reservation.setEndTime(LocalDateTime.now().plusHours(1));
    reservation.setVehicleId(TEST_ID_1);

    ReservationDto reservationDto = new ReservationDto();
    reservationDto.setId(TEST_ID_1);
    reservationDto.setParkingSpotLocation(PARKING_SPOT_LOCATION);
    reservationDto.setStartTime(LocalDateTime.now());
    reservationDto.setEndTime(LocalDateTime.now().plusHours(2));
    reservationDto.setVehicleId(TEST_ID_1);

    when(reservationRepository.findById(TEST_ID_1)).thenReturn(Optional.of(reservation));
    when(parkingSpotService.findByLocation(PARKING_SPOT_LOCATION)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(ParkingSpotNotFoundException.class, () -> {
      reservationService.updateReservation(reservationDto);
    });
  }

  @Test
  void testAddReservation_UserAndParkingSpotExist() {
    // Arrange
    User user = new User();
    user.setUsername(Constants.USERNAME);

    ParkingSpot parkingSpot = new ParkingSpot();
    parkingSpot.setLocation(PARKING_SPOT_LOCATION);

    VehicleView vehicleView = new VehicleView();
    vehicleView.setId(TEST_ID_1);

    ReservationDto reservationDto = new ReservationDto();
    reservationDto.setParkingSpotLocation(PARKING_SPOT_LOCATION);
    reservationDto.setStartTime(Constants.TEST_DATE_TIME_1);
    reservationDto.setEndTime(Constants.TEST_DATE_TIME_2);
    reservationDto.setVehicleId(TEST_ID_1);

    when(userService.findByUsername(Constants.USERNAME)).thenReturn(Optional.of(user));
    when(parkingSpotService.findByLocation(PARKING_SPOT_LOCATION)).thenReturn(Optional.of(parkingSpot));
    when(vehicleService.getVehicleById(TEST_ID_1)).thenReturn(vehicleView);

    // Act
    reservationService.addReservation(reservationDto, Constants.USERNAME);

    // Assert
    verify(reservationRepository).save(any(Reservation.class));
    assertFalse(parkingSpot.isAvailable());
  }

  @Test
  void testAddReservation_UserNotFound() {
    // Arrange
    ReservationDto reservationDto = new ReservationDto();
    reservationDto.setParkingSpotLocation(PARKING_SPOT_LOCATION);
    reservationDto.setStartTime(Constants.TEST_DATE_TIME_1);
    reservationDto.setEndTime(Constants.TEST_DATE_TIME_2);
    reservationDto.setVehicleId(TEST_ID_1);

    when(userService.findByUsername(Constants.USERNAME)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(UsernameNotFoundException.class, () -> {
      reservationService.addReservation(reservationDto, Constants.USERNAME);
    });
  }

  @Test
  void testAddReservation_ParkingSpotNotFound() {
    // Arrange
    User user = new User();
    user.setUsername(Constants.USERNAME);

    ReservationDto reservationDto = new ReservationDto();
    reservationDto.setParkingSpotLocation(PARKING_SPOT_LOCATION);
    reservationDto.setStartTime(Constants.TEST_DATE_TIME_1);
    reservationDto.setEndTime(Constants.TEST_DATE_TIME_2);
    reservationDto.setVehicleId(TEST_ID_1);

    when(userService.findByUsername(Constants.USERNAME)).thenReturn(Optional.of(user));
    when(parkingSpotService.findByLocation(PARKING_SPOT_LOCATION)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(ParkingSpotNotFoundException.class, () -> {
      reservationService.addReservation(reservationDto, Constants.USERNAME);
    });
  }

  @Test
  void testCreateNewReservation_UserAndParkingSpotExist() {
    // Arrange
    User user = new User();
    user.setUsername(Constants.USERNAME);

    ParkingSpot parkingSpot = new ParkingSpot();
    parkingSpot.setLocation(PARKING_SPOT_LOCATION);

    VehicleView vehicleView = new VehicleView();
    vehicleView.setId(TEST_ID_1);

    NewReservationDto newReservationDto = new NewReservationDto();
    newReservationDto.setParkingSpotId(TEST_ID_1);
    newReservationDto.setStartTime(Constants.TEST_DATE_TIME_1);
    newReservationDto.setEndTime(Constants.TEST_DATE_TIME_2);
    newReservationDto.setVehicleId(TEST_ID_1);

    when(userService.findByUsername(Constants.USERNAME)).thenReturn(Optional.of(user));
    when(parkingSpotService.findById(TEST_ID_1)).thenReturn(Optional.of(parkingSpot));
    when(vehicleService.getVehicleById(TEST_ID_1)).thenReturn(vehicleView);

    // Act
    reservationService.createNewReservation(newReservationDto, Constants.USERNAME);

    // Assert
    verify(reservationRepository).save(any(Reservation.class));
    assertFalse(parkingSpot.isAvailable());
  }

  @Test
  void testCreateNewReservation_UserNotFound() {
    // Arrange
    NewReservationDto newReservationDto = new NewReservationDto();
    newReservationDto.setParkingSpotId(TEST_ID_1);
    newReservationDto.setStartTime(Constants.TEST_DATE_TIME_1);
    newReservationDto.setEndTime(Constants.TEST_DATE_TIME_2);
    newReservationDto.setVehicleId(TEST_ID_1);

    when(userService.findByUsername(Constants.USERNAME)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(UsernameNotFoundException.class, () -> {
      reservationService.createNewReservation(newReservationDto, Constants.USERNAME);
    });
  }

  @Test
  void testCreateNewReservation_ParkingSpotNotFound() {
    // Arrange
    User user = new User();
    user.setUsername(Constants.USERNAME);

    NewReservationDto newReservationDto = new NewReservationDto();
    newReservationDto.setParkingSpotId(TEST_ID_1);
    newReservationDto.setStartTime(Constants.TEST_DATE_TIME_1);
    newReservationDto.setEndTime(Constants.TEST_DATE_TIME_2);
    newReservationDto.setVehicleId(TEST_ID_1);

    when(userService.findByUsername(Constants.USERNAME)).thenReturn(Optional.of(user));
    when(parkingSpotService.findById(TEST_ID_1)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(ParkingSpotNotFoundException.class, () -> {
      reservationService.createNewReservation(newReservationDto, Constants.USERNAME);
    });
  }

  @Test
  void testDeleteReservation_ReservationNotFound() {
    // Arrange
    when(reservationRepository.findById(TEST_ID_1)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(ReservationNotFoundException.class, () -> {
      reservationService.deleteReservation(TEST_ID_1);
    });
  }

  @Test
  void testDeleteReservation_Success() {
    // Arrange
    ParkingSpot parkingSpot = new ParkingSpot();
    parkingSpot.setLocation(PARKING_SPOT_LOCATION);

    Reservation reservation = new Reservation();
    reservation.setId(TEST_ID_1);
    reservation.setParkingSpot(parkingSpot);
    reservation.setStartTime(Constants.TEST_DATE_TIME_1);
    reservation.setEndTime(Constants.TEST_DATE_TIME_2);
    reservation.setVehicleId(TEST_ID_1);

    VehicleView vehicleView = new VehicleView();
    vehicleView.setId(TEST_ID_1);
    vehicleView.setLicensePlate(Constants.VEHICLE_LICENSE_PLATE);

    when(reservationRepository.findById(TEST_ID_1)).thenReturn(Optional.of(reservation));
    when(vehicleService.getVehicleById(TEST_ID_1)).thenReturn(vehicleView);

    // Act
    reservationService.deleteReservation(TEST_ID_1);

    // Assert
    verify(reservationRepository).deleteById(TEST_ID_1);
    verify(parkingSpotService).makeSpotAvailable(PARKING_SPOT_LOCATION);
  }

  @Test
  void testGetFormattedReservationById_ReservationExists() {
    // Arrange
    ParkingSpot parkingSpot = new ParkingSpot();
    parkingSpot.setLocation(PARKING_SPOT_LOCATION);

    Reservation reservation = new Reservation();
    reservation.setId(TEST_ID_1);
    reservation.setParkingSpot(parkingSpot);
    reservation.setStartTime(Constants.TEST_DATE_TIME_1);
    reservation.setEndTime(Constants.TEST_DATE_TIME_2);
    reservation.setVehicleId(TEST_ID_1);

    VehicleView vehicleView = new VehicleView();
    vehicleView.setId(TEST_ID_1);
    vehicleView.setLicensePlate(Constants.VEHICLE_LICENSE_PLATE);

    when(reservationRepository.findById(TEST_ID_1)).thenReturn(Optional.of(reservation));
    when(vehicleService.getVehicleById(TEST_ID_1)).thenReturn(vehicleView);

    // Act
    ReservationDto result = reservationService.getFormattedReservationById(TEST_ID_1);

    // Assert
    assertEquals(TEST_ID_1, result.getId());
    assertEquals(PARKING_SPOT_LOCATION, result.getParkingSpotLocation());
    assertEquals(DateUtil.formatDate(Constants.TEST_DATE_TIME_1), result.getStartTime());
    assertEquals(DateUtil.formatDate(Constants.TEST_DATE_TIME_2), result.getEndTime());
    assertEquals(TEST_ID_1, result.getVehicleId());
    assertEquals(Constants.VEHICLE_LICENSE_PLATE, result.getVehicleLicensePlate());
  }

  @Test
  void testGetFormattedReservationById_ReservationNotFound() {
    // Arrange
    when(reservationRepository.findById(TEST_ID_1)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(ReservationNotFoundException.class, () -> {
      reservationService.getFormattedReservationById(TEST_ID_1);
    });
  }

  @Test
  void testFindAllForAdmin() {
    // Arrange
    ParkingSpot parkingSpot = new ParkingSpot();
    parkingSpot.setLocation(PARKING_SPOT_LOCATION);

    Reservation reservation = new Reservation();
    reservation.setId(TEST_ID_1);
    reservation.setParkingSpot(parkingSpot);
    reservation.setStartTime(Constants.TEST_DATE_TIME_1);
    reservation.setEndTime(Constants.TEST_DATE_TIME_2);
    reservation.setVehicleId(TEST_ID_1);

    VehicleView vehicleView = new VehicleView();
    vehicleView.setId(TEST_ID_1);
    vehicleView.setLicensePlate(Constants.VEHICLE_LICENSE_PLATE);

    when(reservationRepository.findAll()).thenReturn(List.of(reservation));
    when(vehicleService.getVehicleById(TEST_ID_1)).thenReturn(vehicleView);

    // Act
    List<ReservationAdminView> result = reservationService.findAllForAdmin();

    // Assert
    assertEquals(1, result.size());
    assertEquals(TEST_ID_1, result.get(0).getId());
    assertEquals(PARKING_SPOT_LOCATION, result.get(0).getParkingSpotLocation());
    assertEquals(Constants.TEST_DATE_TIME_1, result.get(0).getStartTime());
    assertEquals(Constants.TEST_DATE_TIME_2, result.get(0).getEndTime());
    assertEquals(Constants.VEHICLE_LICENSE_PLATE, result.get(0).getLicencePlate());
  }

  @Test
  void testGetUserReservations_UserNotFound() {
    // Arrange
    when(userService.findByUsername(Constants.USERNAME)).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(UsernameNotFoundException.class, () -> {
      reservationService.getUserReservations(Constants.USERNAME);
    });
  }

  @Test
  void testGetUserReservations_NoReservations() {
    // Arrange
    Role userRole = new Role();
    userRole.setName(UserRoleEnum.USER);

    User user = new User();
    user.setUsername(Constants.USERNAME);
    user.setRoles(Set.of(userRole));

    when(userService.findByUsername(Constants.USERNAME)).thenReturn(Optional.of(user));
    when(reservationRepository.findByUser(user)).thenReturn(List.of());

    // Act
    List<ReservationDto> result = reservationService.getUserReservations(Constants.USERNAME);

    // Assert
    assertTrue(result.isEmpty());
  }

  @Test
  void testUpdateReservation_VehicleNotFound() {
    // Arrange
    ParkingSpot parkingSpot = new ParkingSpot();
    parkingSpot.setLocation(PARKING_SPOT_LOCATION);

    Reservation reservation = new Reservation();
    reservation.setId(TEST_ID_1);
    reservation.setParkingSpot(parkingSpot);
    reservation.setStartTime(Constants.TEST_DATE_TIME_1);
    reservation.setEndTime(Constants.TEST_DATE_TIME_2);
    reservation.setVehicleId(TEST_ID_1);

    ReservationDto reservationDto = new ReservationDto();
    reservationDto.setId(TEST_ID_1);
    reservationDto.setParkingSpotLocation(PARKING_SPOT_LOCATION);
    reservationDto.setStartTime(Constants.TEST_DATE_TIME_1);
    reservationDto.setEndTime(Constants.TEST_DATE_TIME_2);
    reservationDto.setVehicleId(TEST_ID_1);

    when(reservationRepository.findById(TEST_ID_1)).thenReturn(Optional.of(reservation));
    when(parkingSpotService.findByLocation(PARKING_SPOT_LOCATION)).thenReturn(Optional.of(parkingSpot));
    when(vehicleService.getVehicleById(TEST_ID_1)).thenReturn(null);

    // Act & Assert
    assertThrows(RuntimeException.class, () -> {
      reservationService.updateReservation(reservationDto);
    });
  }

  @Test
  void testFindAll() {
    // Arrange
    Reservation reservation1 = new Reservation();
    reservation1.setId(TEST_ID_1);

    Reservation reservation2 = new Reservation();
    reservation2.setId(TEST_ID_2);

    when(reservationRepository.findAll()).thenReturn(List.of(reservation1, reservation2));

    // Act
    List<Reservation> result = reservationService.findAll();

    // Assert
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(TEST_ID_1, result.get(0).getId());
    assertEquals(TEST_ID_2, result.get(1).getId());
  }
}
