package bg.softuni.parking.model.dto.vehicle;


public class VehicleView {
  private long id;
  private String licensePlate;
  
  public VehicleView() {
  }
  
  public long getId() {
    return id;
  }
  
  public VehicleView setId(long id) {
    this.id = id;
    return this;
  }

    public VehicleView(long id, String licensePlate) {
      this.id = id;
      this.licensePlate = licensePlate;
    }

  
  public String getLicensePlate() {
    return licensePlate;
  }
  
  public void setLicensePlate(String licensePlate) {
    this.licensePlate = licensePlate;
  }
}
