package bg.softuni.parking.service;

import bg.softuni.parking.model.entities.Role;
import bg.softuni.parking.model.enums.UserRoleEnum;
import bg.softuni.parking.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static bg.softuni.parking.Constants.TEST_ID_1;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {
  
  @Mock
  private RoleRepository roleRepository;
  
  @InjectMocks
  private RoleService roleService;
  
  @Test
  void testFindRoleByName_RoleExists() {
    // Arrange
    Role role = new Role();
    role.setId(TEST_ID_1);
    role.setName(UserRoleEnum.ADMIN);
    
    when(roleRepository.findByName(UserRoleEnum.ADMIN)).thenReturn(Optional.of(role));
    
    // Act
    Optional<Role> result = roleService.findRoleByName(UserRoleEnum.ADMIN);
    
    // Assert
    assertTrue(result.isPresent());
    assertEquals(UserRoleEnum.ADMIN, result.get().getName());
    
    verify(roleRepository).findByName(UserRoleEnum.ADMIN);
  }
  
  @Test
  void testFindRoleByName_RoleNotExists() {
    // Arrange
    when(roleRepository.findByName(UserRoleEnum.ADMIN)).thenReturn(Optional.empty());
    
    // Act
    Optional<Role> result = roleService.findRoleByName(UserRoleEnum.ADMIN);
    
    // Assert
    assertTrue(result.isEmpty());
    
    verify(roleRepository).findByName(UserRoleEnum.ADMIN);
  }
  
  @Test
  void testSaveRole() {
    // Arrange
    Role role = new Role();
    role.setId(TEST_ID_1);
    role.setName(UserRoleEnum.ADMIN);
    
    when(roleRepository.save(role)).thenReturn(role);
    
    // Act
    Role result = roleService.saveRole(role);
    
    // Assert
    assertEquals(TEST_ID_1, result.getId());
    assertEquals(UserRoleEnum.ADMIN, result.getName());
    
    verify(roleRepository).save(role);
  }
  
}
