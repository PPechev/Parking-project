//package bg.softuni.parking.model.dto;
//
//import java.time.LocalDateTime;
//
//public class ReservationDto {
//    private Long id;
//    private String parkingSpotLocation;
//    private LocalDateTime startTime;
//    private LocalDateTime endTime;
//
//    // Getters and setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
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
//}

package bg.softuni.parking.model.dto;

import java.time.LocalDateTime;

public class ReservationDto {

    private Long id;
    private String parkingSpotLocation;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long vehicleId;


    // Getters and Setters


    public Long getVehicleId() {
        return vehicleId;
    }

    public ReservationDto setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getParkingSpotLocation() {
        return parkingSpotLocation;
    }

    public void setParkingSpotLocation(String parkingSpotLocation) {
        this.parkingSpotLocation = parkingSpotLocation;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
