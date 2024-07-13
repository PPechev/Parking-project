package bg.softuni.parking.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "parking_spots")
public class ParkingSpotEntity extends BaseEntity {

    @Column(nullable = false)
    private String location;

    private boolean isOccupied ;


    public ParkingSpotEntity() {
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public ParkingSpotEntity setOccupied(boolean occupied) {
        isOccupied = occupied;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public ParkingSpotEntity setLocation(String location) {
        this.location = location;
        return this;
    }
}
