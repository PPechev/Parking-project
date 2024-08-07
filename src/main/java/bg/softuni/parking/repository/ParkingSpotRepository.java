package bg.softuni.parking.repository;

import bg.softuni.parking.model.entities.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    List<ParkingSpot> findAllByAvailable(boolean available);

    Optional<ParkingSpot> findByLocation(String location);

    Optional<ParkingSpot> findById(Long id);


}
