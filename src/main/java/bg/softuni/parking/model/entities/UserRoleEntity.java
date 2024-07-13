package bg.softuni.parking.model.entities;

import bg.softuni.parking.model.enums.UserRoleEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class UserRoleEntity extends BaseEntity {


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;


    public UserRoleEntity() {
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public UserRoleEntity setRole(UserRoleEnum role) {
        this.role = role;
        return this;
    }
}
