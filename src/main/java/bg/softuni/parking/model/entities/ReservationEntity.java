package bg.softuni.parking.model.entities;

import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class ReservationEntity extends BaseEntity{


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "parking_spot_id")
    private ParkingSpotEntity parkingSpot;
    @Column(nullable = false)
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public ReservationEntity() {
    }


    public UserEntity getUser() {
        return user;
    }

    public ReservationEntity setUser(UserEntity user) {
        this.user = user;
        return this;
    }

    public ParkingSpotEntity getParkingSpot() {
        return parkingSpot;
    }

    public ReservationEntity setParkingSpot(ParkingSpotEntity parkingSpot) {
        this.parkingSpot = parkingSpot;
        return this;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public ReservationEntity setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public ReservationEntity setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }
}
