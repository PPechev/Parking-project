

package bg.softuni.parking.service;

import bg.softuni.parking.exception.ParkingSpotNotFoundException;
import bg.softuni.parking.exception.ReservationNotFoundException;
import bg.softuni.parking.exception.UsernameNotFoundException;
import bg.softuni.parking.model.dto.NewReservationDto;
import bg.softuni.parking.model.dto.ReservationDto;
import bg.softuni.parking.model.dto.reservationAdminView.ReservationAdminView;
import bg.softuni.parking.model.dto.vehicle.VehicleView;
import bg.softuni.parking.model.entities.ParkingSpot;
import bg.softuni.parking.model.entities.Reservation;
import bg.softuni.parking.model.entities.User;
import bg.softuni.parking.repository.ReservationRepository;
import bg.softuni.parking.utils.DateUtil;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ParkingSpotService parkingSpotService;
    private final UserService userService;
    private final VehicleService vehicleService;

    public ReservationService(ReservationRepository reservationRepository,
                              ParkingSpotService parkingSpotService, UserService userService, VehicleService vehicleService) {
        this.reservationRepository = reservationRepository;
        this.parkingSpotService = parkingSpotService;
        this.userService = userService;
        this.vehicleService = vehicleService;
    }





      public List<ReservationDto> getUserReservations(String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return reservationRepository.findByUser(user).stream()
                .map(this::mapToReservationDto)
                .collect(Collectors.toList());
    }




              public ReservationDto getReservationById(Long id) {
            Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
            return mapToReservationDto(reservation);
          }



                public void updateReservation(ReservationDto reservationDto) {
            Reservation reservation = reservationRepository.findById(reservationDto.getId())
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
            ParkingSpot parkingSpot = parkingSpotService.findByLocation(reservationDto.getParkingSpotLocation())
                .orElseThrow(() -> new ParkingSpotNotFoundException("Parking spot not found"));
            VehicleView vehicle = vehicleService.getVehicleById(reservationDto.getVehicleId());


            reservation.setParkingSpot(parkingSpot);
            reservation.setStartTime(reservationDto.getStartTime());
            reservation.setEndTime(reservationDto.getEndTime());
            reservation.setVehicleId(vehicle.getId());
            reservation.getParkingSpot().setAvailable(false);
            reservationRepository.save(reservation);
          }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    private ReservationDto mapToReservationDto(Reservation reservation) {
        ReservationDto dto = new ReservationDto();
        dto.setId(reservation.getId());
        dto.setParkingSpotLocation(reservation.getParkingSpot().getLocation());
        dto.setStartTime(reservation.getStartTime());
        dto.setEndTime(reservation.getEndTime());
        dto.setVehicleId(reservation.getVehicleId());
        dto.setVehicleLicensePlate(vehicleService.getVehicleById(reservation.getVehicleId()).getLicensePlate());
        return dto;
    }



              public void addReservation(ReservationDto reservationDto, String username) {
            User user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
            ParkingSpot parkingSpot = parkingSpotService.findByLocation(reservationDto.getParkingSpotLocation())
                .orElseThrow(() -> new ParkingSpotNotFoundException("Parking spot not found"));
            VehicleView vehicle = vehicleService.getVehicleById(reservationDto.getVehicleId());


            Reservation reservation = new Reservation();
            reservation.setParkingSpot(parkingSpot);
            reservation.setStartTime(reservationDto.getStartTime());
            reservation.setEndTime(reservationDto.getEndTime());
            reservation.setUser(user);

            reservation.setVehicleId(vehicle.getId());
            parkingSpot.setAvailable(false);
            reservationRepository.save(reservation);
          }





          public void createNewReservation(NewReservationDto newReservationDto, String username) {
        User user = userService.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        ParkingSpot parkingSpot = parkingSpotService.findById(newReservationDto.getParkingSpotId())
            .orElseThrow(() -> new ParkingSpotNotFoundException("Parking spot not found"));
        VehicleView vehicle = vehicleService.getVehicleById(newReservationDto.getVehicleId());

        Reservation reservation = new Reservation();
        reservation.setParkingSpot(parkingSpot);
        reservation.setStartTime(newReservationDto.getStartTime());
        reservation.setEndTime(newReservationDto.getEndTime());
        reservation.setUser(user);
        newReservationDto.setParkingSpotLocation(parkingSpot.getLocation());
        reservation.setVehicleId(vehicle.getId());
        parkingSpot.setAvailable(false);

        reservationRepository.save(reservation);
      }





          public void deleteReservation(Long id) {
        parkingSpotService.makeSpotAvailable(getReservationById(id)
            .getParkingSpotLocation());
        Reservation reservation = reservationRepository.findById(id)
            .orElseThrow(() -> new ReservationNotFoundException("Reservation not found"));
        reservation.setItPaid(true);
        reservationRepository.deleteById(id);
      }



    //     additional
    public ReservationDto getFormattedReservationById(Long id) {
        ReservationDto reservation = getReservationById(id);
        reservation.setStartTime(DateUtil.formatDate(reservation.getStartTime()));
        reservation.setEndTime(DateUtil.formatDate(reservation.getEndTime()));
        return reservation;
    }

    public List<ReservationAdminView> findAllForAdmin() {
        return reservationRepository.findAll().stream().map(reservation ->
                        new ReservationAdminView()
                                .setId(reservation.getId())
                                .setParkingSpotLocation(reservation.getParkingSpot().getLocation())
                                .setStartTime(reservation.getStartTime())
                                .setEndTime(reservation.getEndTime())
                                .setLicencePlate(vehicleService.getVehicleById(reservation.getVehicleId()).getLicensePlate()))
                .collect(Collectors.toList());
    }
}