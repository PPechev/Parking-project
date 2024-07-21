//package bg.softuni.parking.model.dto;
//
//public class UserLoginDto {
//
//    private String username;
//
//    private String password;
//
//    public UserLoginDto() {}
//
//    public String getUsername() {
//        return username;
//    }
//
//
//    public String getPassword() {
//        return password;
//    }
//
//
//}


package bg.softuni.parking.model.dto;

import jakarta.validation.constraints.NotBlank;

public class UserLoginDto {

    @NotBlank(message = "Потребителското име е задължително")
    private String username;

    @NotBlank(message = "Паролата е задължителна")
    private String password;

    // Getters and setters...

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
