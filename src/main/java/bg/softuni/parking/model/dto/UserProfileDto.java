//
//
//package bg.softuni.parking.model.dto;
//
//import bg.softuni.parking.model.entities.Reservation;
//import bg.softuni.parking.model.entities.Vehicle;
//
//import java.util.Set;
//
//public class UserProfileDto {
//    private String username;
//    private String email;
//    private String firstName;
//    private String lastName;
//    private String phone;
//    private Set<String> roles;
//    private Set<Reservation> reservations;
//    private Set<Vehicle> vehicles;
//
//    // Getters and setters
//    // ...ю
//
//    public String getUsername() {
//        return username;
//    }
//
//    public UserProfileDto setUsername(String username) {
//        this.username = username;
//        return this;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public UserProfileDto setEmail(String email) {
//        this.email = email;
//        return this;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public UserProfileDto setFirstName(String firstName) {
//        this.firstName = firstName;
//        return this;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public UserProfileDto setLastName(String lastName) {
//        this.lastName = lastName;
//        return this;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public UserProfileDto setPhone(String phone) {
//        this.phone = phone;
//        return this;
//    }
//
//    public Set<String> getRoles() {
//        return roles;
//    }
//
//    public UserProfileDto setRoles(Set<String> roles) {
//        this.roles = roles;
//        return this;
//    }
//
//    public Set<Reservation> getReservations() {
//        return reservations;
//    }
//
//    public UserProfileDto setReservations(Set<Reservation> reservations) {
//        this.reservations = reservations;
//        return this;
//    }
//
//    public Set<Vehicle> getVehicles() {
//        return vehicles;
//    }
//
//    public UserProfileDto setVehicles(Set<Vehicle> vehicles) {
//        this.vehicles = vehicles;
//        return this;
//    }
//}


//package bg.softuni.parking.model.dto;
//
//import bg.softuni.parking.model.entities.Vehicle;
//
//import java.util.Set;
//
//public class UserProfileDto {
//    private String username;
//    private String email;
//    private String firstName;
//    private String lastName;
//    private String phone;
//    private Set<String> roles;
//    private Set<ReservationDto> reservations;
//    private Set<Vehicle> vehicles;
//
//    // Getters and setters
//    // ...
//
//    public String getUsername() {
//        return username;
//    }
//
//    public UserProfileDto setUsername(String username) {
//        this.username = username;
//        return this;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public UserProfileDto setEmail(String email) {
//        this.email = email;
//        return this;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public UserProfileDto setFirstName(String firstName) {
//        this.firstName = firstName;
//        return this;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public UserProfileDto setLastName(String lastName) {
//        this.lastName = lastName;
//        return this;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public UserProfileDto setPhone(String phone) {
//        this.phone = phone;
//        return this;
//    }
//
//    public Set<String> getRoles() {
//        return roles;
//    }
//
//    public UserProfileDto setRoles(Set<String> roles) {
//        this.roles = roles;
//        return this;
//    }
//
//    public Set<ReservationDto> getReservations() {
//        return reservations;
//    }
//
//    public UserProfileDto setReservations(Set<ReservationDto> reservations) {
//        this.reservations = reservations;
//        return this;
//    }
//
//    public Set<Vehicle> getVehicles() {
//        return vehicles;
//    }
//
//    public UserProfileDto setVehicles(Set<Vehicle> vehicles) {
//        this.vehicles = vehicles;
//        return this;
//    }
//}


package bg.softuni.parking.model.dto;

import java.util.List;

public class UserProfileDto {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private List<ReservationDto> reservations;
    private List<VehicleDto> vehicles;


// Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<ReservationDto> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationDto> reservations) {
        this.reservations = reservations;
    }
    public List<VehicleDto> getVehicles() {
        return vehicles;
    }

    public UserProfileDto setVehicles(List<VehicleDto> vehicles) {
        this.vehicles = vehicles;
        return this;
    }
}
