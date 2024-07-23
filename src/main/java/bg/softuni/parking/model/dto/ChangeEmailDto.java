package bg.softuni.parking.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ChangeEmailDto {

    @NotBlank(message = "Новият имейл е задължителен")
    @Email(message = "Невалиден имейл адрес")
    private String newEmail;

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
