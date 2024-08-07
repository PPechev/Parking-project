
package bg.softuni.parking.service;

import bg.softuni.parking.exception.BankCardNotFoundException;
import bg.softuni.parking.exception.UsernameNotFoundException;
import bg.softuni.parking.model.dto.BankCardDto;
import bg.softuni.parking.model.entities.BankCard;
import bg.softuni.parking.model.entities.User;
import bg.softuni.parking.repository.BankCardRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankCardService {

    private final BankCardRepository bankCardRepository;
    private final UserService userService;

    public BankCardService(BankCardRepository bankCardRepository, UserService userService) {
        this.bankCardRepository = bankCardRepository;

        this.userService = userService;
    }


    public List<BankCardDto> getBankCardsByUsername(String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        return user.getBankCards().stream().map(this::mapToBankCardDto)
                .collect(Collectors.toList());
    }


    public BankCardDto getBankCardById(Long id) {
        BankCard bankCard = bankCardRepository.findById(id)
                .orElseThrow(() -> new BankCardNotFoundException("Bank card not found"));
        return mapToBankCardDto(bankCard);
    }


    public void addBankCard(BankCardDto bankCardDto, String username) {
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        BankCard bankCard = new BankCard();
        bankCard.setCardNumber(bankCardDto.getCardNumber());
        bankCard.setUser(user);
        bankCardRepository.save(bankCard);
    }


    public void updateBankCard(BankCardDto bankCardDto) {
        BankCard bankCard = bankCardRepository.findById(bankCardDto.getId())
                .orElseThrow(() -> new BankCardNotFoundException("Bank card not found"));
        bankCard.setCardNumber(bankCardDto.getCardNumber());
        bankCardRepository.save(bankCard);
    }

    public void deleteBankCard(Long id) {
        bankCardRepository.deleteById(id);
    }

    private BankCardDto mapToBankCardDto(BankCard bankCard) {
        BankCardDto dto = new BankCardDto();
        dto.setId(bankCard.getId());
        dto.setCardNumber(bankCard.getCardNumber());
        return dto;
    }
}
