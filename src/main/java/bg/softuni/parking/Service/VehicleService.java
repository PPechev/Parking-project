package bg.softuni.parking.Service;

import bg.softuni.parking.model.entities.User;
import bg.softuni.parking.model.entities.Vehicle;
import bg.softuni.parking.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {


    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
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
}
