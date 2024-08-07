package bg.softuni.parking.service;

import bg.softuni.parking.exception.BankCardNotFoundException;
import bg.softuni.parking.exception.UsernameNotFoundException;
import bg.softuni.parking.model.dto.BankCardDto;
import bg.softuni.parking.model.entities.BankCard;
import bg.softuni.parking.model.entities.User;
import bg.softuni.parking.repository.BankCardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static bg.softuni.parking.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankCardServiceTest {
    @Mock
    private BankCardRepository bankCardRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private BankCardService bankCardService;

    @Test
    void testGetBankCardsByUsername_UserExists() {
        // Arrange
        User user = new User();
        user.setUsername(USERNAME);
        BankCard bankCard = new BankCard();
        bankCard.setId(TEST_ID_1);
        bankCard.setCardNumber(BANK_CARD_NUMBER);
        bankCard.setUser(user);
        user.setBankCards(Set.of(bankCard));

        when(userService.findByUsername(USERNAME)).thenReturn(Optional.of(user));

        // Act
        List<BankCardDto> result = bankCardService.getBankCardsByUsername(USERNAME);

        // Assert
        assertEquals(1, result.size());
        assertEquals(TEST_ID_1, result.get(0).getId());
        assertEquals(BANK_CARD_NUMBER, result.get(0).getCardNumber());

        verify(userService).findByUsername(USERNAME);
    }

    @Test
    void testGetBankCardsByUsername_UserNotFound() {
        // Arrange
        when(userService.findByUsername(USERNAME)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            bankCardService.getBankCardsByUsername(USERNAME);
        });

        verify(userService).findByUsername(USERNAME);
    }

    @Test
    void testGetBankCardById_BankCardExists() {
        // Arrange
        BankCard bankCard = new BankCard();
        bankCard.setId(TEST_ID_1);
        bankCard.setCardNumber(BANK_CARD_NUMBER);

        when(bankCardRepository.findById(TEST_ID_1)).thenReturn(Optional.of(bankCard));

        // Act
        BankCardDto result = bankCardService.getBankCardById(TEST_ID_1);

        // Assert
        assertEquals(TEST_ID_1, result.getId());
        assertEquals(BANK_CARD_NUMBER, result.getCardNumber());

        verify(bankCardRepository).findById(TEST_ID_1);
    }

    @Test
    void testGetBankCardById_BankCardNotFound() {
        // Arrange
        when(bankCardRepository.findById(TEST_ID_1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BankCardNotFoundException.class, () -> {
            bankCardService.getBankCardById(TEST_ID_1);
        });

        verify(bankCardRepository).findById(TEST_ID_1);
    }

    @Test
    void testAddBankCard_UserExists() {
        // Arrange
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setCardNumber(BANK_CARD_NUMBER);
        User user = new User();
        user.setUsername(USERNAME);

        when(userService.findByUsername(USERNAME)).thenReturn(Optional.of(user));
        when(bankCardRepository.save(any(BankCard.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        bankCardService.addBankCard(bankCardDto, USERNAME);

        // Assert
        verify(userService).findByUsername(USERNAME);
        verify(bankCardRepository).save(any(BankCard.class));
    }

    @Test
    void testAddBankCard_UserNotFound() {
        // Arrange
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setCardNumber(BANK_CARD_NUMBER);

        when(userService.findByUsername(USERNAME)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            bankCardService.addBankCard(bankCardDto, USERNAME);
        });

        verify(userService).findByUsername(USERNAME);
        verify(bankCardRepository, never()).save(any(BankCard.class));
    }

    @Test
    void testUpdateBankCard_BankCardExists() {
        // Arrange
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setId(TEST_ID_1);
        bankCardDto.setCardNumber(BANK_CARD_NUMBER);

        BankCard bankCard = new BankCard();
        bankCard.setId(TEST_ID_1);
        bankCard.setCardNumber(OLD_BANK_CARD_NUMBER);

        when(bankCardRepository.findById(TEST_ID_1)).thenReturn(Optional.of(bankCard));
        when(bankCardRepository.save(any(BankCard.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        bankCardService.updateBankCard(bankCardDto);

        // Assert
        assertEquals(BANK_CARD_NUMBER, bankCard.getCardNumber());
        verify(bankCardRepository).findById(TEST_ID_1);
        verify(bankCardRepository).save(bankCard);
    }

    @Test
    void testUpdateBankCard_BankCardNotFound() {
        // Arrange
        BankCardDto bankCardDto = new BankCardDto();
        bankCardDto.setId(TEST_ID_1);
        bankCardDto.setCardNumber(BANK_CARD_NUMBER);

        when(bankCardRepository.findById(TEST_ID_1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(BankCardNotFoundException.class, () -> {
            bankCardService.updateBankCard(bankCardDto);
        });

        verify(bankCardRepository).findById(TEST_ID_1);
        verify(bankCardRepository, never()).save(any(BankCard.class));
    }

    @Test
    void testDeleteBankCard() {
        // Arrange
        doNothing().when(bankCardRepository).deleteById(TEST_ID_1);

        // Act
        bankCardService.deleteBankCard(TEST_ID_1);

        // Assert
        verify(bankCardRepository).deleteById(TEST_ID_1);
    }


    @Test
    void testSaveBankCard() {
        BankCard bankCard = new BankCard();
        bankCard.setId(1L);

        when(bankCardRepository.save(bankCard)).thenReturn(bankCard);

        BankCard saved = bankCardRepository.save(bankCard);

        assertNotNull(saved);
        assertEquals(1L, saved.getId());
    }


}

