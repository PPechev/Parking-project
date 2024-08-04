package bg.softuni.parking.model.dto.reservationAdminView;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public class ReservationAdminView {
  private Long id;
  private String parkingSpotLocation;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime startTime;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private LocalDateTime endTime;
  private String licencePlate;
  
  public ReservationAdminView() {
  }
  
  
  public Long getId() {
    return id;
  }
  
  public ReservationAdminView setId(Long id) {
    this.id = id;
    return this;
  }
  
  public String getParkingSpotLocation() {
    return parkingSpotLocation;
  }
  
  public ReservationAdminView setParkingSpotLocation(String parkingSpotLocation) {
    this.parkingSpotLocation = parkingSpotLocation;
    return this;
  }
  
  public LocalDateTime getStartTime() {
    return startTime;
  }
  
  public ReservationAdminView setStartTime(LocalDateTime startTime) {
    this.startTime = startTime;
    return this;
  }
  
  public LocalDateTime getEndTime() {
    return endTime;
  }
  
  public ReservationAdminView setEndTime(LocalDateTime endTime) {
    this.endTime = endTime;
    return this;
  }
  
  public String getLicencePlate() {
    return licencePlate;
  }
  
  public ReservationAdminView setLicencePlate(String licencePlate) {
    this.licencePlate = licencePlate;
    return this;
  }
}
