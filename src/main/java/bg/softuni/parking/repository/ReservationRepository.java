package bg.softuni.parking.repository;

import bg.softuni.parking.model.dto.ReservationDto;
import bg.softuni.parking.model.entities.Reservation;
import bg.softuni.parking.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface ReservationRepository  extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByUser(User user);

    List<Reservation> findByUserUsername(String username);


    List<Reservation> findByUser(User user);
}
