//package bg.softuni.parking.model.dto;
//
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.time.LocalDateTime;
//
//public class ReservationDto {
//
//    private Long id;
//    private String parkingSpotLocation;
//    @DateTimeFormat(pattern = "yyyy-MM-dd-hh-mm")
//    private LocalDateTime startTime;
//    @DateTimeFormat(pattern = "yyyy-MM-dd-hh-mm")
//    private LocalDateTime endTime;
//    private Long vehicleId;
//    private String vehicleLicensePlate; // TODO
//
//
//    // Getters and Setters
//
//
//    public String getVehicleLicensePlate() {
//        return vehicleLicensePlate;
//    }
//
//    public ReservationDto setVehicleLicensePlate(String vehicleLicensePlate) {
//        this.vehicleLicensePlate = vehicleLicensePlate;
//        return this;
//    }
//
//    public Long getVehicleId() {
//        return vehicleId;
//    }
//
//    public ReservationDto setVehicleId(Long vehicleId) {
//        this.vehicleId = vehicleId;
//        return this;
//    }
//
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

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class ReservationDto {

    private Long id;
    private String parkingSpotLocation;
    //    @DateTimeFormat(pattern = "yyyy-MM-dd-hh-mm")
//    private LocalDateTime startTime;
//    @DateTimeFormat(pattern = "yyyy-MM-dd-hh-mm")
//    private LocalDateTime endTime;

    //vanya
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime startTime;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endTime;

    private Long vehicleId;
    private String vehicleLicensePlate; // TODO

    private Long bankCardId;
    private String bankCardNumber;




    // Getters and Setters


    public Long getBankCardId() {
        return bankCardId;
    }

    public ReservationDto setBankCardId(Long bankCardId) {
        this.bankCardId = bankCardId;
        return this;
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public ReservationDto setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
        return this;
    }

    public String getVehicleLicensePlate() {
        return vehicleLicensePlate;
    }

    public ReservationDto setVehicleLicensePlate(String vehicleLicensePlate) {
        this.vehicleLicensePlate = vehicleLicensePlate;
        return this;
    }

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