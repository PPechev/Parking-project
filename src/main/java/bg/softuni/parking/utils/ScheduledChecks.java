package bg.softuni.parking.utils;

import bg.softuni.parking.model.entities.Reservation;
import bg.softuni.parking.service.ReservationService;
import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ScheduledChecks {


    private final ReservationService reservationService;

    public ScheduledChecks(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Transactional
    @Scheduled(cron = "0 * * * * ?")
    public void cleanParkingSpots() {

        List<Reservation> allReservations = reservationService.findAll();

        for (Reservation reservation : allReservations) {
            if (reservation.getEndTime().isBefore(LocalDateTime.now())) {

                reservationService.deleteReservation(reservation.getId());
            }

        }
    }

}
