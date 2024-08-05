package bg.softuni.parking.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserUpdateDto {

    @NotNull
    @Size(min = 3,  message = "Полето трябва да съдържа поне 3 символа!")
    private String username;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(max = 15 , message = "Полето не може да съдържа повече от 15 символа!")
    @Pattern(regexp = "^[A-Za-z]+$" , message = "Полето трябва да съдържа само букви!")
    private String firstName;
    @NotNull
    @Size(max = 15 , message = "Полето не може да съдържа повече от 15 символа!")
    @Pattern(regexp = "^[A-Za-z]+$" , message = "Полето трябва да съдържа само букви!")
    private String lastName;
    @NotNull
    @Pattern(regexp = "^\\d+$", message = "Полето трябва да съдържа само цифри")
    private String phone;



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
