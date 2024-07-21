//package bg.softuni.parking.model.dto;
//
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotEmpty;
//import jakarta.validation.constraints.Size;
//
//public class UserRegistrationDto {
//    @NotBlank(message = "Username cannot be empty!")
//    @Size(message = "Username length must be between 3 and 30 symbols!",min = 3, max = 20)
//    private String username;
//    @Email(message = "Email must be valid ! ")
//    @NotBlank(message = "Email cannot me empty!")
//    private String email;
//
//    @NotBlank
//    @Size(min = 6 ,message = "Password must be minimum 6 symbols!")
//    private String password;
//
//    @NotBlank(message = "Потвърждението на паролата е задължително")
//    private String confirmPassword;
//
//    public String getConfirmPassword() {
//        return confirmPassword;
//    }
//
//    public UserRegistrationDto setConfirmPassword(String confirmPassword) {
//        this.confirmPassword = confirmPassword;
//        return this;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public UserRegistrationDto setEmail( String email) {
//        this.email = email;
//        return this;
//    }
//
//
//    public UserRegistrationDto() {
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public UserRegistrationDto setUsername(String username) {
//        this.username = username;
//        return this;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public UserRegistrationDto setPassword(String password) {
//        this.password = password;
//        return this;
//    }
//}


package bg.softuni.parking.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegistrationDto {

    @NotBlank(message = "Потребителското име е задължително")
    @Size(min = 3, max = 20, message = "Потребителското име трябва да бъде между 3 и 20 символа")
    private String username;

    @NotBlank(message = "Паролата е задължителна")
    @Size(min = 6, message = "Паролата трябва да бъде поне 6 символа")
    private String password;

    @NotBlank(message = "Имейлът е задължителен")
    @Email(message = "Невалиден имейл адрес")
    private String email;

    @NotBlank(message = "Потвърждението на паролата е задължително")
    private String confirmPassword;



    public String getUsername() {
        return username;
    }

    public UserRegistrationDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserRegistrationDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserRegistrationDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public UserRegistrationDto setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
