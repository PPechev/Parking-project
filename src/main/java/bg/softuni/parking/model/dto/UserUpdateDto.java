package bg.softuni.parking.model.dto;

public class UserUpdateDto {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public UserUpdateDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserUpdateDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserUpdateDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserUpdateDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserUpdateDto setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
