


package bg.softuni.parking.web;

import bg.softuni.parking.model.dto.BankCardDto;
import bg.softuni.parking.service.BankCardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bank-cards")
public class BankCardController {

    private final BankCardService bankCardService;

    public BankCardController(BankCardService bankCardService) {
        this.bankCardService = bankCardService;
    }

    @GetMapping
    public String getBankCards(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails != null) {
            List<BankCardDto> bankCards = bankCardService.getBankCardsByUsername(userDetails.getUsername());
            model.addAttribute("bankCards", bankCards);
        }
        return "bank-cards";
    }

    @GetMapping("/add")
    public String addBankCardForm(Model model) {
        model.addAttribute("bankCard", new BankCardDto());
        return "bank-card-add";
    }

    @PostMapping("/add")
    public String addBankCard(@ModelAttribute BankCardDto bankCardDto, @AuthenticationPrincipal UserDetails userDetails) {
        bankCardService.addBankCard(bankCardDto, userDetails.getUsername());
        return "redirect:/bank-cards";
    }

    @GetMapping("/edit/{id}")
    public String editBankCardForm(@PathVariable Long id, Model model) {
        BankCardDto bankCard = bankCardService.getBankCardById(id);
        model.addAttribute("bankCard", bankCard);
        return "bank-card-edit";
    }

    @PostMapping("/edit")
    public String editBankCard(@ModelAttribute BankCardDto bankCardDto) {
        bankCardService.updateBankCard(bankCardDto);
        return "redirect:/bank-cards";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBankCard(@PathVariable Long id) {
        bankCardService.deleteBankCard(id);
        return "redirect:/bank-cards";
    }
}
