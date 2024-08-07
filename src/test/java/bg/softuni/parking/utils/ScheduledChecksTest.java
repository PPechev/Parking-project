package bg.softuni.parking.utils;

import bg.softuni.parking.model.entities.Reservation;
import bg.softuni.parking.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduledChecksTest {

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private ScheduledChecks scheduledChecks;

    @BeforeEach
    void setUp() {
        scheduledChecks = new ScheduledChecks(reservationService);
    }

    @Test
    void testCleanParkingSpots() {
        // Arrange
        Reservation reservation1 = new Reservation();
        reservation1.setId(1L);
        reservation1.setEndTime(LocalDateTime.now().minusHours(1));

        Reservation reservation2 = new Reservation();
        reservation2.setId(2L);
        reservation2.setEndTime(LocalDateTime.now().plusHours(1));

        when(reservationService.findAll()).thenReturn(List.of(reservation1, reservation2));

        // Act
        scheduledChecks.cleanParkingSpots();

        // Assert
        verify(reservationService, times(1)).deleteReservation(1L);
        verify(reservationService, times(0)).deleteReservation(2L);
    }
}
