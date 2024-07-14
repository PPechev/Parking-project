package bg.softuni.parking.model.entities;

import bg.softuni.parking.model.enums.UserRoleEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role  extends BaseEntity{


    private UserRoleEnum name;



    @ManyToMany(mappedBy = "roles")
    private Set<User> users;


    public Role() {
        this.users = new HashSet<>();
    }

    public UserRoleEnum getName() {
        return name;
    }

    public Role setName(UserRoleEnum name) {
        this.name = name;
        return this;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Role setUsers(Set<User> users) {
        this.users = users;
        return this;
    }
}
