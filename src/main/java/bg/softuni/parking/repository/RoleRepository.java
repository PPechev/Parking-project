package bg.softuni.parking.repository;

import bg.softuni.parking.model.entities.Role;
import bg.softuni.parking.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(UserRoleEnum name);

}
