package bg.softuni.parking.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Vehicles")
public class Vehicle  extends BaseEntity{

    @Column(nullable = false)
    private String licensePlate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private  User owner;


    public Vehicle() {
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Vehicle setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
        return this;
    }

    public User getOwner() {
        return owner;
    }

    public Vehicle setOwner(User owner) {
        this.owner = owner;
        return this;
    }
}
