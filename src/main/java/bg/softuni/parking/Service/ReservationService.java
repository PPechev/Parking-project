package bg.softuni.parking.Service;

import bg.softuni.parking.model.entities.Reservation;
import bg.softuni.parking.model.entities.User;
import bg.softuni.parking.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> findAllByUser(User user) {
        return reservationRepository.findAllByUser(user);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }
}
