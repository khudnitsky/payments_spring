package by.pvt.khudnitsky.payments.pojos;

import by.pvt.khudnitsky.payments.enums.CurrencyType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Wrapper-class on CurrencyType
 * Created by: khudnitsky
 * Date: 06.02.2016
 * Time: 17:16
 */
@Entity
public class Currency extends AbstractEntity{
    private static final long serialVersionUID = 9L;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "enum('BYR', 'USD', 'EUR')", length = 3)
    public CurrencyType getCurrencyType() {
        return currencyType;
    }
    public void setCurrencyType(CurrencyType currency) {
        this.currencyType = currency;
    }
    private CurrencyType currencyType;

    @OneToMany(mappedBy = "currency", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    public Set<Account> getAccounts() {
        return accounts;
    }
    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
    private Set<Account> accounts;

    public Currency() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;
        if (!super.equals(o)) return false;

        Currency currency1 = (Currency) o;

        return currencyType == currency1.currencyType;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (currencyType != null ? currencyType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "currency=" + currencyType +
                '}';
    }

    /**
     * Secured adding into set
     * @param account - entity of Account
     */
    public void addAccount(Account account){
        if(accounts == null){
            accounts = new HashSet<>();
        }
        accounts.add(account);
    }
}
