package bg.softuni.parking.service;

import bg.softuni.parking.model.entities.ParkingSpot;
import bg.softuni.parking.repository.ParkingSpotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSpotService {


    private final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotService(ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotRepository = parkingSpotRepository;
    }

    public ParkingSpot saveParkingSpot(ParkingSpot parkingSpot) {
        return parkingSpotRepository.save(parkingSpot);
    }

    public List<ParkingSpot> findAllAvailable() {
        return parkingSpotRepository.findAllByAvailable(true);
    }

    public List<ParkingSpot> findAll() {
        return parkingSpotRepository.findAll();
    }
}
