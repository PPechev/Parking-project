


package bg.softuni.parking.service;

import bg.softuni.parking.model.dto.NewReservationDto;
import bg.softuni.parking.model.dto.ReservationDto;
import bg.softuni.parking.model.entities.*;
import bg.softuni.parking.repository.*;
import bg.softuni.parking.utils.DateUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ParkingSpotRepository parkingSpotRepository;
    private final VehicleRepository vehicleRepository;
    private final ParkingSpotService parkingSpotService;
    private final BankCardRepository bankCardRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              UserRepository userRepository,
                              ParkingSpotRepository parkingSpotRepository,
                              VehicleRepository vehicleRepository,
                              ParkingSpotService parkingSpotService, BankCardRepository bankCardRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.parkingSpotRepository = parkingSpotRepository;
        this.vehicleRepository = vehicleRepository;
        this.parkingSpotService = parkingSpotService;

        this.bankCardRepository = bankCardRepository;
    }

    public List<ReservationDto> getUserReservations(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return reservationRepository.findByUser(user).stream()
                .map(this::mapToReservationDto)
                .collect(Collectors.toList());
    }



    public ReservationDto getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        return mapToReservationDto(reservation);
    }

    public void updateReservation(ReservationDto reservationDto) {
        Reservation reservation = reservationRepository.findById(reservationDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        ParkingSpot parkingSpot = parkingSpotRepository.findByLocation(reservationDto.getParkingSpotLocation())
                .orElseThrow(() -> new IllegalArgumentException("Parking spot not found"));
        Vehicle vehicle = vehicleRepository.findById(reservationDto.getVehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));

        reservation.setParkingSpot(parkingSpot);
        reservation.setStartTime(reservationDto.getStartTime());
        reservation.setEndTime(reservationDto.getEndTime());
        reservation.setVehicle(vehicle);
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
        dto.setVehicleId(reservation.getVehicle().getId());
        dto.setVehicleLicensePlate(reservation.getVehicle().getLicensePlate());
        return dto;
    }





    // това е старият метод за добавяне на резервация  без банковите датайли РАБОТИ ! ! !


    public void addReservation(ReservationDto reservationDto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        ParkingSpot parkingSpot = parkingSpotRepository.findByLocation(reservationDto.getParkingSpotLocation())
                .orElseThrow(() -> new IllegalArgumentException("Parking spot not found"));
        Vehicle vehicle = vehicleRepository.findById(reservationDto.getVehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        Reservation reservation = new Reservation();
        reservation.setParkingSpot(parkingSpot);
        reservation.setStartTime(reservationDto.getStartTime());
        reservation.setEndTime(reservationDto.getEndTime());
        reservation.setUser(user);

        reservation.setVehicle(vehicle);
        parkingSpot.setAvailable(false);
        reservationRepository.save(reservation);
    }



    public void createNewReservation(NewReservationDto newReservationDto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        ParkingSpot parkingSpot = parkingSpotRepository.findById(newReservationDto.getParkingSpotId())
                .orElseThrow(() -> new IllegalArgumentException("Parking spot not found"));
        Vehicle vehicle = vehicleRepository.findById(newReservationDto.getVehicleId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        Reservation reservation = new Reservation();
        reservation.setParkingSpot(parkingSpot);
        reservation.setStartTime(newReservationDto.getStartTime());
        reservation.setEndTime(newReservationDto.getEndTime());
        reservation.setUser(user);
        newReservationDto.setParkingSpotLocation(parkingSpot.getLocation());
        reservation.setVehicle(vehicle);
        parkingSpot.setAvailable(false);

        reservationRepository.save(reservation);
    }


    public void deleteReservation(Long id) {
        parkingSpotService.makeSpotAvailable(getReservationById(id).getParkingSpotLocation());
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Reservation not found"));
        reservation.setItPaid(true);
        reservationRepository.deleteById(id);
    }

    //    vanya additional
    public ReservationDto getFormattedReservationById(Long id) {
        ReservationDto reservation = getReservationById(id);
        reservation.setStartTime(DateUtil.formatDate(reservation.getStartTime()));
        reservation.setEndTime(DateUtil.formatDate(reservation.getEndTime()));
        return reservation;
    }
}