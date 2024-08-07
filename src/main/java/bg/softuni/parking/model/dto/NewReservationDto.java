package bg.softuni.parking.model.dto;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

public class NewReservationDto {


    private Long parkingSpotId;
    private String parkingSpotLocation;
    @DateTimeFormat(pattern = "yyyy-MM-dd-hh-mm")
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd-hh-mm")
    private LocalDateTime endTime;
    private Long vehicleId;

    private Long bankCardId;
    private String bankCardNumber;


    public Long getBankCardId() {
        return bankCardId;
    }

    public NewReservationDto setBankCardId(Long bankCardId) {
        this.bankCardId = bankCardId;
        return this;
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public NewReservationDto setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
        return this;
    }

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
