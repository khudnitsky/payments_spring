/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Describes the entity <b>Card</b>
 * @author khudnitsky
 * @version 1.0
 *
 */

@Entity
public class Card extends AbstractEntity {
    private static final long serialVersionUID = 5L;

    @Column(nullable = false, updatable = false)
    public Integer getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(Integer cardNumber) {
        this.cardNumber = cardNumber;
    }
    private Integer cardNumber;

    @Temporal(value = TemporalType.TIMESTAMP) // TODO разобраться
    @Column(nullable = false, updatable = false)
    public Calendar getValidity() {
        return validity;
    }
    public void setValidity(Calendar validity) {
        this.validity = validity;
    }
    private Calendar validity;

    @ManyToOne
    @JoinColumn(name = "F_ACCOUNT_ID", updatable = false)
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    private Account account;

    public Card() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        if (!super.equals(o)) return false;

        Card card = (Card) o;

        if (validity != null ? !validity.equals(card.validity) : card.validity != null) return false;
        return account != null ? account.equals(card.account) : card.account == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (validity != null ? validity.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Card{" +
                "validity=" + validity +
                ", account=" + account +
                '}';
    }
}
