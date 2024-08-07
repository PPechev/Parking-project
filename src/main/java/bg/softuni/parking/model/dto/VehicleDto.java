package bg.softuni.parking.model.dto;

public class VehicleDto {

    private long id;

    private String licensePlate;


    public long getId() {
        return id;
    }

    public VehicleDto setId(long id) {
        this.id = id;
        return this;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
