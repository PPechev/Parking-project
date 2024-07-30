package bg.softuni.parking.model.dto;

import java.time.LocalDateTime;

public class ReservationWithIdDto {


    private Long id;
    private String parkingSpotLocation;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long vehicleId;
    private String vehicleLicensePlate;

    public Long getId() {
        return id;
    }

    public ReservationWithIdDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getParkingSpotLocation() {
        return parkingSpotLocation;
    }

    public ReservationWithIdDto setParkingSpotLocation(String parkingSpotLocation) {
        this.parkingSpotLocation = parkingSpotLocation;
        return this;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public ReservationWithIdDto setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public ReservationWithIdDto setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public ReservationWithIdDto setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public String getVehicleLicensePlate() {
        return vehicleLicensePlate;
    }

    public ReservationWithIdDto setVehicleLicensePlate(String vehicleLicensePlate) {
        this.vehicleLicensePlate = vehicleLicensePlate;
        return this;
    }
}
