package bg.softuni.parking.model.dto;

import jakarta.validation.constraints.NotBlank;

public class ChangePasswordDto {

    @NotBlank(message = "Старата парола е задължителна")
    private String oldPassword;

    @NotBlank(message = "Новата парола е задължителна")
    private String newPassword;

    @NotBlank(message = "Потвърждението на новата парола е задължително")
    private String confirmNewPassword;


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
