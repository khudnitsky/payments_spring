/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.pojos;

import javax.persistence.*;
import java.util.Calendar;

/**
 * Describes the entity <b>Operation</b>
 * @author khudnitsky
 * @version 1.0
 *
 */

@Entity
public class Operation extends AbstractEntity {
    private static final long serialVersionUID = 4L;

    @Column(nullable = false, precision = 2, updatable = false)
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    private Double amount;   //TODO BigDecimal

    @Column(nullable = false, length = 50, updatable = false)
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    private String description;

    @Temporal(value = TemporalType.TIMESTAMP) // TODO разобраться
    @Column(nullable = false, updatable = false)
    public Calendar getDate() {
        return date;
    }
    public void setDate(Calendar date) {
        this.date = date;
    }
    private Calendar date;

    @ManyToOne
    @JoinColumn(name = "F_USER_ID", updatable = false)
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    private User user;

    @ManyToOne
    @JoinColumn(name = "F_ACCOUNT_ID", updatable = false)
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    private Account account;

    public Operation() {
        super();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Operation)) return false;
        if (!super.equals(o)) return false;

        Operation operation = (Operation) o;

        if (user != null ? !user.equals(operation.user) : operation.user != null) return false;
        if (account != null ? !account.equals(operation.account) : operation.account != null) return false;
        if (amount != null ? !amount.equals(operation.amount) : operation.amount != null) return false;
        if (description != null ? !description.equals(operation.description) : operation.description != null)
            return false;
        return date != null ? Math.abs(date.getTimeInMillis() - operation.date.getTimeInMillis()) < 1000 : operation.date == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "user=" + user +
                ", account=" + account +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", date=" + date +
                '}';
    }
}