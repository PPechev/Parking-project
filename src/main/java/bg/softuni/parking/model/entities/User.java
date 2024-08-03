//package bg.softuni.parking.model.entities;
//
//import jakarta.persistence.*;
//
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//@Table(name = "users")
//public class User extends BaseEntity {
//
//    @Column(unique = true, nullable = false)
//    private String username;
//    @Column(nullable = false)
//    private String password;
//    @Column(nullable = false , unique = true )
//    private String email;
//    private String phone;
//
//
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "user_roles",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id")
//    )
//    private Set<Role> roles;
//
//
//    private String firstName;
//    private String lastName;
//
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public User setPhone(String phone) {
//        this.phone = phone;
//        return this;
//    }
//
//
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public User setFirstName(String firstName) {
//        this.firstName = firstName;
//        return this;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public User setLastName(String lastName) {
//        this.lastName = lastName;
//        return this;
//    }
//
//
//    public User() {
//        this.roles = new HashSet<>();
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public User setUsername(String username) {
//        this.username = username;
//        return this;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public User setPassword(String password) {
//        this.password = password;
//        return this;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public User setEmail(String email) {
//        this.email = email;
//        return this;
//    }
//
//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    public User setRoles(Set<Role> roles) {
//        this.roles = roles;
//        return this;
//    }
//}


package bg.softuni.parking.model.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reservation> reservations = new HashSet<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Vehicle> vehicles = new HashSet<>();

    @Transient
    private Long selectedVehicleId;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BankCard> bankCards = new HashSet<>();

    // Getters and setters


    public Set<BankCard> getBankCards() {
        return bankCards;
    }

    public User setBankCards(Set<BankCard> bankCards) {
        this.bankCards = bankCards;
        return this;
    }

    public Long getSelectedVehicleId() {
        return selectedVehicleId;
    }

    public void setSelectedVehicleId(Long selectedVehicleId) {
        this.selectedVehicleId = selectedVehicleId;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public User setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public User setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
        return this;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public User setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
        return this;
    }
}
