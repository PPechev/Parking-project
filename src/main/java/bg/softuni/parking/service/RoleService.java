package bg.softuni.parking.service;

import bg.softuni.parking.model.entities.Role;
import bg.softuni.parking.model.enums.UserRoleEnum;
import bg.softuni.parking.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> findRoleByName(UserRoleEnum roleName) {
        return roleRepository.findByName(roleName);
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
}
