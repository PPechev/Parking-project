package bg.softuni.parking.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "bank_cards")
public class BankCard extends BaseEntity {

    @Column(nullable = false)
    private String cardNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public BankCard() {
    }


    public String getCardNumber() {
        return cardNumber;
    }

    public BankCard setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public User getUser() {
        return user;
    }

    public BankCard setUser(User user) {
        this.user = user;
        return this;
    }
}
