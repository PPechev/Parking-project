package bg.softuni.parking.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "parking_spots")
public class ParkingSpot extends BaseEntity {

    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private boolean available;

    public ParkingSpot() {
        this.available = true;
    }


    public String getLocation() {
        return location;
    }

    public ParkingSpot setLocation(String location) {
        this.location = location;
        return this;
    }


    public boolean isAvailable() {
        return available;
    }

    public ParkingSpot setAvailable(boolean available) {
        this.available = available;
        return this;
    }

}
