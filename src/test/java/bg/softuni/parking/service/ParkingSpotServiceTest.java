package bg.softuni.parking.service;

import bg.softuni.parking.Constants;
import bg.softuni.parking.exception.ParkingSpotNotFoundException;
import bg.softuni.parking.model.entities.ParkingSpot;
import bg.softuni.parking.repository.ParkingSpotRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static bg.softuni.parking.Constants.TEST_ID_2;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParkingSpotServiceTest {
  
  @Mock
  private ParkingSpotRepository parkingSpotRepository;
  
  @InjectMocks
  private ParkingSpotService parkingSpotService;
  
  @Test
  void testSaveParkingSpot() {
    // Arrange
    ParkingSpot parkingSpot = new ParkingSpot();
    parkingSpot.setId(Constants.TEST_ID_1);
    parkingSpot.setLocation(Constants.BANK_CARD_NUMBER);
    
    when(parkingSpotRepository.save(any(ParkingSpot.class))).thenReturn(parkingSpot);
    
    // Act
    ParkingSpot result = parkingSpotService.saveParkingSpot(parkingSpot);
    
    // Assert
    assertEquals(Constants.TEST_ID_1, result.getId());
    assertEquals(Constants.BANK_CARD_NUMBER, result.getLocation());
    
    verify(parkingSpotRepository).save(any(ParkingSpot.class));
  }
  
  @Test
  void testFindById_ParkingSpotExists() {
    // Arrange
    ParkingSpot parkingSpot = new ParkingSpot();
    parkingSpot.setId(Constants.TEST_ID_1);
    parkingSpot.setLocation(Constants.BANK_CARD_NUMBER);
    
    when(parkingSpotRepository.findById(Constants.TEST_ID_1)).thenReturn(Optional.of(parkingSpot));
    
    // Act
    Optional<ParkingSpot> result = parkingSpotService.findById(Constants.TEST_ID_1);
    
    // Assert
    assertEquals(true, result.isPresent());
    assertEquals(Constants.TEST_ID_1, result.get().getId());
    assertEquals(Constants.BANK_CARD_NUMBER, result.get().getLocation());
    
    verify(parkingSpotRepository).findById(Constants.TEST_ID_1);
  }
  
  @Test
  void testFindById_ParkingSpotNotExists() {
    // Arrange
    when(parkingSpotRepository.findById(Constants.TEST_ID_1)).thenReturn(Optional.empty());
    
    // Act
    Optional<ParkingSpot> result = parkingSpotService.findById(Constants.TEST_ID_1);
    
    // Assert
    assertEquals(false, result.isPresent());
    
    verify(parkingSpotRepository).findById(Constants.TEST_ID_1);
  }
  
  @Test
  void testFindAllAvailable() {
    // Arrange
    ParkingSpot parkingSpot1 = new ParkingSpot();
    parkingSpot1.setId(Constants.TEST_ID_1);
    parkingSpot1.setLocation(Constants.BANK_CARD_NUMBER);
    parkingSpot1.setAvailable(true);
    
    ParkingSpot parkingSpot2 = new ParkingSpot();
    parkingSpot2.setId(TEST_ID_2);
    parkingSpot2.setLocation(Constants.ANOTHER_LOCATION);
    parkingSpot2.setAvailable(true);
    
    when(parkingSpotRepository.findAllByAvailable(true)).thenReturn(List.of(parkingSpot1, parkingSpot2));
    
    // Act
    List<ParkingSpot> result = parkingSpotService.findAllAvailable();
    
    // Assert
    assertEquals(2, result.size());
    assertEquals(Constants.TEST_ID_1, result.get(0).getId());
    assertEquals(Constants.BANK_CARD_NUMBER, result.get(0).getLocation());
    assertEquals(TEST_ID_2, result.get(1).getId());
    assertEquals(Constants.ANOTHER_LOCATION, result.get(1).getLocation());
    
    verify(parkingSpotRepository).findAllByAvailable(true);
  }
  
  @Test
  void testFindAll() {
    // Arrange
    ParkingSpot parkingSpot1 = new ParkingSpot();
    parkingSpot1.setId(Constants.TEST_ID_1);
    parkingSpot1.setLocation(Constants.BANK_CARD_NUMBER);
    parkingSpot1.setAvailable(true);
    
    ParkingSpot parkingSpot2 = new ParkingSpot();
    parkingSpot2.setId(TEST_ID_2);
    parkingSpot2.setLocation(Constants.ANOTHER_LOCATION);
    parkingSpot2.setAvailable(false);
    
    when(parkingSpotRepository.findAll()).thenReturn(List.of(parkingSpot1, parkingSpot2));
    
    // Act
    List<ParkingSpot> result = parkingSpotService.findAll();
    
    // Assert
    assertEquals(2, result.size());
    assertEquals(Constants.TEST_ID_1, result.get(0).getId());
    assertEquals(Constants.BANK_CARD_NUMBER, result.get(0).getLocation());
    assertEquals(TEST_ID_2, result.get(1).getId());
    assertEquals(Constants.ANOTHER_LOCATION, result.get(1).getLocation());
    
    verify(parkingSpotRepository).findAll();
  }
  
  @Test
  void testGetLocationById_ParkingSpotExists() {
    // Arrange
    ParkingSpot parkingSpot = new ParkingSpot();
    parkingSpot.setId(Constants.TEST_ID_1);
    parkingSpot.setLocation(Constants.BANK_CARD_NUMBER);
    
    when(parkingSpotRepository.findById(Constants.TEST_ID_1)).thenReturn(Optional.of(parkingSpot));
    
    // Act
    String result = parkingSpotService.getLocationById(Constants.TEST_ID_1);
    
    // Assert
    assertEquals(Constants.BANK_CARD_NUMBER, result);
    
    verify(parkingSpotRepository).findById(Constants.TEST_ID_1);
  }
  
  @Test
  void testGetLocationById_ParkingSpotNotExists() {
    // Arrange
    when(parkingSpotRepository.findById(Constants.TEST_ID_1)).thenReturn(Optional.empty());
    
    // Act & Assert
    assertThrows(ParkingSpotNotFoundException.class, () -> {
      parkingSpotService.getLocationById(Constants.TEST_ID_1);
    });
    
    verify(parkingSpotRepository).findById(Constants.TEST_ID_1);
  }
  @Test
  void testGetCurrentParkingSpotById_ParkingSpotExists() {
    // Arrange
    ParkingSpot parkingSpot = new ParkingSpot();
    parkingSpot.setId(Constants.TEST_ID_1);
    parkingSpot.setLocation(Constants.BANK_CARD_NUMBER);
    
    when(parkingSpotRepository.findById(Constants.TEST_ID_1)).thenReturn(Optional.of(parkingSpot));
    
    // Act
    ParkingSpot result = parkingSpotService.getCurrentParkingSpotById(Constants.TEST_ID_1);
    
    // Assert
    assertEquals(Constants.TEST_ID_1, result.getId());
    assertEquals(Constants.BANK_CARD_NUMBER, result.getLocation());
    
    verify(parkingSpotRepository).findById(Constants.TEST_ID_1);
  }
  
  @Test
  void testGetCurrentParkingSpotById_ParkingSpotNotExists() {
    // Arrange
    when(parkingSpotRepository.findById(Constants.TEST_ID_1)).thenReturn(Optional.empty());
    
    // Act & Assert
    assertThrows(ParkingSpotNotFoundException.class, () -> {
      parkingSpotService.getCurrentParkingSpotById(Constants.TEST_ID_1);
    });
    
    verify(parkingSpotRepository).findById(Constants.TEST_ID_1);
  }
  @Test
  void testMakeSpotAvailable_ParkingSpotExists() {
    // Arrange
    ParkingSpot parkingSpot = new ParkingSpot();
    parkingSpot.setId(Constants.TEST_ID_1);
    parkingSpot.setLocation(Constants.BANK_CARD_NUMBER);
    parkingSpot.setAvailable(false);
    
    when(parkingSpotRepository.findByLocation(Constants.BANK_CARD_NUMBER)).thenReturn(Optional.of(parkingSpot));
    when(parkingSpotRepository.save(parkingSpot)).thenReturn(parkingSpot);
    
    // Act
    parkingSpotService.makeSpotAvailable(Constants.BANK_CARD_NUMBER);
    
    // Assert
    assertTrue(parkingSpot.isAvailable());
    verify(parkingSpotRepository).findByLocation(Constants.BANK_CARD_NUMBER);
    verify(parkingSpotRepository).save(parkingSpot);
  }

  
  @Test
  void testMakeSpotAvailable_ParkingSpotNotExists() {
    // Arrange
    when(parkingSpotRepository.findByLocation(Constants.BANK_CARD_NUMBER)).thenReturn(Optional.empty());
    
    // Act & Assert
    assertThrows(ParkingSpotNotFoundException.class, () -> {
      parkingSpotService.makeSpotAvailable(Constants.BANK_CARD_NUMBER);
    });
    
    verify(parkingSpotRepository).findByLocation(Constants.BANK_CARD_NUMBER);
    verify(parkingSpotRepository, never()).save(any(ParkingSpot.class));
  }
  @Test
  void testFindByLocation_ParkingSpotExists() {
    // Arrange
    ParkingSpot parkingSpot = new ParkingSpot();
    parkingSpot.setId(Constants.TEST_ID_1);
    parkingSpot.setLocation(Constants.BANK_CARD_NUMBER);
    
    when(parkingSpotRepository.findByLocation(Constants.BANK_CARD_NUMBER)).thenReturn(Optional.of(parkingSpot));
    
    // Act
    Optional<ParkingSpot> result = parkingSpotService.findByLocation(Constants.BANK_CARD_NUMBER);
    
    // Assert
    assertTrue(result.isPresent());
    assertEquals(Constants.TEST_ID_1, result.get().getId());
    assertEquals(Constants.BANK_CARD_NUMBER, result.get().getLocation());
    
    verify(parkingSpotRepository).findByLocation(Constants.BANK_CARD_NUMBER);
  }
  
  @Test
  void testFindByLocation_ParkingSpotNotExists() {
    // Arrange
    when(parkingSpotRepository.findByLocation(Constants.BANK_CARD_NUMBER)).thenReturn(Optional.empty());
    
    // Act
    Optional<ParkingSpot> result = parkingSpotService.findByLocation(Constants.BANK_CARD_NUMBER);
    
    // Assert
    assertFalse(result.isPresent());
    
    verify(parkingSpotRepository).findByLocation(Constants.BANK_CARD_NUMBER);
  }

  
}
