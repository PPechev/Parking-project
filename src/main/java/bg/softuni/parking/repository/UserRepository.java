package bg.softuni.parking.repository;

import bg.softuni.parking.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    User findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.username = :newUsername AND u.username != :currentUsername")
    boolean existsByUsernameAndNotCurrentUsername(@Param("newUsername") String newUsername, @Param("currentUsername") String currentUsername);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END FROM User u WHERE u.email = :newEmail AND u.email != :currentEmail")
    boolean existsByEmailAndNotCurrentEmail(@Param("newEmail") String newEmail, @Param("currentEmail") String currentEmail);


    User findByUuid(String owner);
}
