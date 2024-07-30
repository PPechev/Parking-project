package bg.softuni.parking.model.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class NewReservationDto {

//    private String parkingSpotLocation;
//    private LocalDateTime startTime;
//    private LocalDateTime endTime;
//    private Long vehicleId;
//
//    // Getters and Setters...
//
//    public String getParkingSpotLocation() {
//        return parkingSpotLocation;
//    }
//
//    public void setParkingSpotLocation(String parkingSpotLocation) {
//        this.parkingSpotLocation = parkingSpotLocation;
//    }
//
//    public LocalDateTime getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(LocalDateTime startTime) {
//        this.startTime = startTime;
//    }
//
//    public LocalDateTime getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(LocalDateTime endTime) {
//        this.endTime = endTime;
//    }
//
//    public Long getVehicleId() {
//        return vehicleId;
//    }
//
//    public void setVehicleId(Long vehicleId) {
//        this.vehicleId = vehicleId;
//    }



    private Long parkingSpotId;
    private String parkingSpotLocation;
    @DateTimeFormat(pattern = "yyyy-MM-dd-hh-mm")
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd-hh-mm")
    private LocalDateTime endTime;
    private Long vehicleId;

    public Long getParkingSpotId() {
        return parkingSpotId;
    }

    public String getParkingSpotLocation() {
        return parkingSpotLocation;
    }

    public NewReservationDto setParkingSpotLocation(String parkingSpotLocation) {
        this.parkingSpotLocation = parkingSpotLocation;
        return this;
    }

    public NewReservationDto setParkingSpotId(Long parkingSpotId) {
        this.parkingSpotId = parkingSpotId;
        return this;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public NewReservationDto setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public NewReservationDto setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public NewReservationDto setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }
}
