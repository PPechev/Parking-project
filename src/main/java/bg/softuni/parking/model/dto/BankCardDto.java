package bg.softuni.parking.model.dto;

public class BankCardDto {

    private long id;

    private String cardNumber;


    public long getId() {
        return id;
    }

    public BankCardDto setId(long id) {
        this.id = id;
        return this;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public BankCardDto setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }
}
