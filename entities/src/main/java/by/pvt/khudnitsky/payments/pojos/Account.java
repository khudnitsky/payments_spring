/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.pojos;

import by.pvt.khudnitsky.payments.enums.AccountStatusType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Describes the entity <b>Account</b>
 * @author khudnitsky
 * @version 1.0
 *
 */

@Entity
public class Account extends AbstractEntity {
    private static final long serialVersionUID = 3L;

    @Column(nullable = false, updatable = false)
    public Long getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }
    private Long accountNumber;

    @Column(nullable = false, precision = 2)
    public Double getDeposit() {
        return deposit;
    }
    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }
    private Double deposit;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('UNBLOCKED', 'BLOCKED')")
    public AccountStatusType getAccountStatus() {
        return accountStatus;
    }
    public void setAccountStatus(AccountStatusType status) {
        this.accountStatus = status;
    }
    private AccountStatusType accountStatus;

    @ManyToOne
    @JoinColumn(name = "F_CURRENCY_ID", updatable = false)
    public Currency getCurrency() {
        return currency;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    private Currency currency;       // TODO 2nd level cache

    @ManyToOne
    @JoinColumn(name = "F_USER_ID", updatable = false)
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    private User user;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<Card> getCards() {
        return cards;
    }
    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }
    private Set<Card> cards;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<Operation> getOperations() {
        return operations;
    }
    public void setOperations(Set<Operation> operations) {
        this.operations = operations;
    }
    private Set<Operation> operations;

    public Account() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        if (!super.equals(o)) return false;

        Account account = (Account) o;

        if (accountNumber != null ? !accountNumber.equals(account.accountNumber) : account.accountNumber != null)
            return false;
        if (deposit != null ? !deposit.equals(account.deposit) : account.deposit != null) return false;
        if (accountStatus != account.accountStatus) return false;
        if (currency != null ? !currency.equals(account.currency) : account.currency != null) return false;
        return user != null ? user.equals(account.user) : account.user == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (accountNumber != null ? accountNumber.hashCode() : 0);
        result = 31 * result + (deposit != null ? deposit.hashCode() : 0);
        result = 31 * result + (accountStatus != null ? accountStatus.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", accountStatus=" + accountStatus +
                ", currency=" + currency +
                ", deposit=" + deposit +
                ", user=" + user +
                '}';
    }

    public void addCard(Card card){
        if(cards == null){
            cards = new HashSet<>();
        }
        cards.add(card);
    }

    public void addOperation(Operation operation){
        if(operations == null){
            operations = new HashSet<>();
        }
        operations.add(operation);
    }
}
