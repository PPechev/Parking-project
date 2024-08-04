package bg.softuni.parking.model.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "vehicle_id", nullable = false)
//    private Vehicle vehicle;


    @Column(nullable = false)
    private Long vehicleId;


    @ManyToOne
    @JoinColumn(name = "parking_spot_id", nullable = false)
    private ParkingSpot parkingSpot;

    @Column(name = "start_time",nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time" ,nullable = false)
    private LocalDateTime endTime;

    private boolean isItPaid ;


    public Long getVehicleId() {
        return vehicleId;
    }

    public Reservation setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public Reservation() {
        this.isItPaid=false;
    }

    public boolean isItPaid() {
        return isItPaid;
    }

    public Reservation setItPaid(boolean itPaid) {
        isItPaid = itPaid;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Reservation setUser(User user) {
        this.user = user;
        return this;
    }



    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public Reservation setParkingSpot(ParkingSpot parkingSpot) {
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
