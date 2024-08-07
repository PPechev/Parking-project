package bg.softuni.parking.repository;


import bg.softuni.parking.model.entities.Reservation;
import bg.softuni.parking.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {


    List<Reservation> findByUser(User user);

    void deleteById(Long id);


    List<Reservation> findAllByVehicleId(Long vehicleId);

}
