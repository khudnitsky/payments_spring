package by.pvt.khudnitsky.payments.entities;

/**
 * Created by: khudnitsky
 * Date: 09.02.2016
 * Time: 9:33
 */
public class OperationDTO {
    private String operationDate;
    private String description;
    private Double amount;
    private String userLastName;
    private Long accountNumber;

    public OperationDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OperationDTO)) return false;

        OperationDTO that = (OperationDTO) o;

        if (operationDate != null ? !operationDate.equals(that.operationDate) : that.operationDate != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (userLastName != null ? !userLastName.equals(that.userLastName) : that.userLastName != null) return false;
        return accountNumber != null ? accountNumber.equals(that.accountNumber) : that.accountNumber == null;

    }

    @Override
    public int hashCode() {
        int result = operationDate != null ? operationDate.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (userLastName != null ? userLastName.hashCode() : 0);
        result = 31 * result + (accountNumber != null ? accountNumber.hashCode() : 0);
        return result;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }
}
