package bg.softuni.parking.service;

import bg.softuni.parking.model.dto.ReservationDto;
import bg.softuni.parking.model.entities.ParkingSpot;
import bg.softuni.parking.model.entities.Reservation;
import bg.softuni.parking.model.entities.User;
import bg.softuni.parking.model.entities.Vehicle;
import bg.softuni.parking.repository.ParkingSpotRepository;
import bg.softuni.parking.repository.ReservationRepository;
import bg.softuni.parking.repository.UserRepository;
import bg.softuni.parking.repository.VehicleRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ParkingSpotRepository parkingSpotRepository;
    private final VehicleRepository vehicleRepository;

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, ParkingSpotRepository parkingSpotRepository, VehicleRepository vehicleRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.parkingSpotRepository = parkingSpotRepository;
        this.vehicleRepository = vehicleRepository;
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
        reservation.setParkingSpot(parkingSpot);
        reservation.setStartTime(reservationDto.getStartTime());
        reservation.setEndTime(reservationDto.getEndTime());
        reservationRepository.save(reservation);
    }

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
        reservationRepository.save(reservation);
    }

    private ReservationDto mapToReservationDto(Reservation reservation) {
        ReservationDto dto = new ReservationDto();
        dto.setId(reservation.getId());
        dto.setParkingSpotLocation(reservation.getParkingSpot().getLocation());
        dto.setStartTime(reservation.getStartTime());
        dto.setEndTime(reservation.getEndTime());
        return dto;
    }


    public List<Reservation> findAll() {

        return reservationRepository.findAll();
    }
}
