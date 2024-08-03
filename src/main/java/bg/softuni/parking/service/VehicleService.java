package bg.softuni.parking.service;

import bg.softuni.parking.model.dto.VehicleDto;
import bg.softuni.parking.model.entities.Reservation;
import bg.softuni.parking.model.entities.User;
import bg.softuni.parking.model.entities.Vehicle;
import bg.softuni.parking.repository.ReservationRepository;
import bg.softuni.parking.repository.UserRepository;
import bg.softuni.parking.repository.VehicleRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {


    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public VehicleService(VehicleRepository vehicleRepository, UserRepository userRepository, ReservationRepository reservationRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }


    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> findAllByUser(User user) {
        return vehicleRepository.findAllByOwner(user);
    }

    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

//    public List<Vehicle> findVehiclesByUsername(String username) {
//        return vehicleRepository.findByOwnerUsername(username);
//    }


    public List<VehicleDto> getUserVehicles(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user.getVehicles().stream()
                .map(this::mapToVehicleDto)
                .collect(Collectors.toList());
    }

    public VehicleDto getVehicleById(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        return mapToVehicleDto(vehicle);
    }

    public void updateVehicle(VehicleDto vehicleDto) {
        Vehicle vehicle = vehicleRepository.findById(vehicleDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        vehicle.setLicensePlate(vehicleDto.getLicensePlate());
        vehicleRepository.save(vehicle);
    }

    public void addVehicle(VehicleDto vehicleDto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(vehicleDto.getLicensePlate());
        vehicle.setOwner(user);
        vehicleRepository.save(vehicle);
    }

    private VehicleDto mapToVehicleDto(Vehicle vehicle) {
        VehicleDto dto = new VehicleDto();
        dto.setId(vehicle.getId());
        dto.setLicensePlate(vehicle.getLicensePlate());
        return dto;
    }

    public boolean hasVehicles(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return !user.getVehicles().isEmpty();
    }

    public void deleteVehicle(Long id) {
        List<Reservation> allByVehicleId = reservationRepository.findAllByVehicleId(id);
        allByVehicleId.forEach(r -> r.getParkingSpot().setAvailable(true));
        allByVehicleId.forEach(reservationRepository::delete);

        vehicleRepository.deleteById(id);
    }
//    public boolean hasReservations(Long vehicleId) {
//        return reservationRepository.existsByVehicleId(vehicleId);
//    }


}
