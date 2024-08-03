//package bg.softuni.parking.service;
//
//import bg.softuni.parking.model.dto.BankCardViewDto;
//import bg.softuni.parking.model.dto.VehicleDto;
//import bg.softuni.parking.model.entities.BankCard;
//import bg.softuni.parking.model.entities.User;
//import bg.softuni.parking.model.entities.Vehicle;
//import bg.softuni.parking.repository.BankCardRepository;
//import bg.softuni.parking.repository.UserRepository;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class BankCardService {
//
//    private final BankCardRepository bankCardRepository;
//    private final UserRepository userRepository;
//
//    public BankCardService(BankCardRepository bankCardRepository, UserRepository userRepository) {
//        this.bankCardRepository = bankCardRepository;
//        this.userRepository = userRepository;
//    }
//
//    public List<BankCard> findAll() {
//        return bankCardRepository.findAll();
//    }
//
//    public BankCard findById(Long id) {
//        return bankCardRepository.findById(id).orElse(null);
//    }
//
//    public BankCard save(BankCard bankCard) {
//        return bankCardRepository.save(bankCard);
//    }
//
//    public void deleteById(Long id) {
//        bankCardRepository.deleteById(id);
//    }
//
//    public List<BankCardViewDto> getUserCards(String username) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        return user.getBankCards().stream()
//                .map(this::mapBankCardViewDto)
//                .collect(Collectors.toList());
//    }
//
//    private BankCardViewDto mapBankCardViewDto(BankCard bankCard) {
//        BankCardViewDto dtoView = new BankCardViewDto();
//        dtoView.setId(bankCard.getId());
//        dtoView.setCardNumber(bankCard.getCardNumber());
//
//        return dtoView;
//    }
//
//}


package bg.softuni.parking.service;

import bg.softuni.parking.model.dto.BankCardDto;
import bg.softuni.parking.model.entities.BankCard;
import bg.softuni.parking.model.entities.User;
import bg.softuni.parking.repository.BankCardRepository;
import bg.softuni.parking.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankCardService {

    private final BankCardRepository bankCardRepository;
    private final UserRepository userRepository;

    public BankCardService(BankCardRepository bankCardRepository, UserRepository userRepository) {
        this.bankCardRepository = bankCardRepository;
        this.userRepository = userRepository;
    }

    public List<BankCardDto> getBankCardsByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        return user.getBankCards().stream().map(this::mapToBankCardDto).collect(Collectors.toList());
    }

    public BankCardDto getBankCardById(Long id) {
        BankCard bankCard = bankCardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Bank card not found"));
        return mapToBankCardDto(bankCard);
    }

    public void addBankCard(BankCardDto bankCardDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("User not found"));
        BankCard bankCard = new BankCard();
        bankCard.setCardNumber(bankCardDto.getCardNumber());
        bankCard.setUser(user);
        bankCardRepository.save(bankCard);
    }

    public void updateBankCard(BankCardDto bankCardDto) {
        BankCard bankCard = bankCardRepository.findById(bankCardDto.getId()).orElseThrow(() -> new IllegalArgumentException("Bank card not found"));
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
