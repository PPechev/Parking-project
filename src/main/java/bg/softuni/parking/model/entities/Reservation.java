package bg.softuni.parking.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


import java.time.LocalDateTime;

@Entity
public class Reservation  extends BaseEntity{


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "parking_spot_id")
    private ParkingSpotEntity parkingSpot;
    @Column(nullable = false)
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Reservation() {
    }


    public UserEntity getUser() {
        return user;
    }

    public Reservation setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public ParkingSpotEntity getParkingSpot() {
        return parkingSpot;
    }

    public Reservation setParkingSpot(ParkingSpotEntity parkingSpot) {
        this.parkingSpot = parkingSpot;
        return this;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Reservation setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Reservation setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }
}
