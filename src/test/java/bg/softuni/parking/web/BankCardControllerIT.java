package bg.softuni.parking.web;

import bg.softuni.parking.model.dto.BankCardDto;
import bg.softuni.parking.service.BankCardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static bg.softuni.parking.Constants.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class BankCardControllerIT {
  
  @Autowired
  private MockMvc mockMvc;
  
  @MockBean
  private BankCardService bankCardService;
  
  @Test
  @WithMockUser(username = USERNAME)
  void testGetBankCards() throws Exception {
    List<BankCardDto> bankCards = List.of(new BankCardDto());
    when(bankCardService.getBankCardsByUsername(USERNAME)).thenReturn(bankCards);
    
    mockMvc.perform(get("/bank-cards"))
        .andExpect(status().isOk())
        .andExpect(view().name("bank-cards"))
        .andExpect(model().attribute("bankCards", bankCards));
    
    verify(bankCardService).getBankCardsByUsername(USERNAME);
  }
  @Test
  @WithMockUser(username = USERNAME)
  void testAddBankCardForm() throws Exception {
    mockMvc.perform(get("/bank-cards/add"))
        .andExpect(status().isOk())
        .andExpect(view().name("bank-card-add"))
        .andExpect(model().attributeExists("bankCard"));
  }
  @Test
  @WithMockUser(username = USERNAME)
  void testAddBankCard() throws Exception {
    BankCardDto bankCardDto = new BankCardDto();
    bankCardDto.setCardNumber(BANK_CARD_NUMBER);
    
    mockMvc.perform(post("/bank-cards/add")
            .flashAttr("bankCardDto", bankCardDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/bank-cards"));
    
    verify(bankCardService).addBankCard(bankCardDto, USERNAME);
  }
  @Test
  @WithMockUser(username = USERNAME)
  void testEditBankCardForm() throws Exception {
    BankCardDto bankCardDto = new BankCardDto();
    when(bankCardService.getBankCardById(TEST_ID_1)).thenReturn(bankCardDto);
    
    mockMvc.perform(get("/bank-cards/edit/{id}", TEST_ID_1))
        .andExpect(status().isOk())
        .andExpect(view().name("bank-card-edit"))
        .andExpect(model().attribute("bankCard", bankCardDto));
    
    verify(bankCardService).getBankCardById(TEST_ID_1);
  }
  @Test
  @WithMockUser(username = USERNAME)
  void testEditBankCard() throws Exception {
    BankCardDto bankCardDto = new BankCardDto();
    bankCardDto.setCardNumber(OLD_BANK_CARD_NUMBER);
    
    mockMvc.perform(post("/bank-cards/edit")
            .flashAttr("bankCardDto", bankCardDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/bank-cards"));
    
    verify(bankCardService).updateBankCard(bankCardDto);
  }
  @Test
  @WithMockUser(username = USERNAME)
  void testDeleteBankCard() throws Exception {
    mockMvc.perform(delete("/bank-cards/delete/{id}", TEST_ID_1))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/bank-cards"));
    
    verify(bankCardService).deleteBankCard(TEST_ID_1);
  }

}
