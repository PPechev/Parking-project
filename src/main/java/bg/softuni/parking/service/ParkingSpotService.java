package bg.softuni.parking.service;

import bg.softuni.parking.model.entities.ParkingSpot;
import bg.softuni.parking.repository.ParkingSpotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public String getLocationById(Long spotId) {
        return parkingSpotRepository.findById(spotId).get().getLocation();

    }

    public ParkingSpot getCurrentParkingSpotById(Long spotId) {
        return parkingSpotRepository.findById(spotId).get();
    }

    public Optional<ParkingSpot> getParkingSpotById(String location) {
        return parkingSpotRepository.findByLocation(location);
    }
}
