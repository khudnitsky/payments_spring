package by.pvt.khudnitsky.payments.dto;

import by.pvt.khudnitsky.payments.entities.Currency;

import javax.validation.constraints.*;

/**
 * Created by: khudnitsky
 * Date: 23.02.2016
 * Time: 11:52
 */
public class UserDTO {
    @Size(min = 2, max = 20, message = "First name should be between 2 and 20 characters long") // TODO локализация
    @Pattern(regexp = "^[a-zA-Zа-яА-Я0-9]+$", message = "First name should be alphanumeric with no spaces")
    @NotNull(message = "First name cannot be empty")
    private String firstName;

    @Size(min = 3, max = 50, message = "Last name should be between 3 and 50 characters long")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я0-9-/s]+$", message = "First name should be alphanumeric with no spaces") // TODO добавить пробелы и тире
    @NotNull(message = "Last name cannot be empty")
    private String lastName;

    @Size(min = 3, max = 20, message = "Login should be between 3 and 20 characters long")
    @NotNull(message = "Login cannot be empty")
    private String login;

    @Size(min = 6, max = 20, message = "Password should be between 3 and 20 characters long")
    @NotNull(message = "Password cannot be empty")
    private String password_1;

    @Size(min = 6, max = 20, message = "Password should be between 3 and 20 characters long")
    @NotNull(message = "Password cannot be empty")
    private String password_2;

    @DecimalMax(value = "100000000", message = "Account number must be a less than 100000000")
    @DecimalMin(value = "1000", message = "Account number must be a greater than 1000")
    @NotNull(message = "Account number cannot be empty")
    private Long accountNumber;

    @Pattern(regexp = "BYR|EUR|USD")
    @Size(min = 3, max = 3, message = "Incorrect value of the currency")
    @NotNull(message = "Currency cannot be empty")
    private String currency;

    public UserDTO() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;

        UserDTO userDTO = (UserDTO) o;

        if (firstName != null ? !firstName.equals(userDTO.firstName) : userDTO.firstName != null) return false;
        if (lastName != null ? !lastName.equals(userDTO.lastName) : userDTO.lastName != null) return false;
        if (login != null ? !login.equals(userDTO.login) : userDTO.login != null) return false;
        if (password_1 != null ? !password_1.equals(userDTO.password_1) : userDTO.password_1 != null) return false;
        if (password_2 != null ? !password_2.equals(userDTO.password_2) : userDTO.password_2 != null) return false;
        if (accountNumber != null ? !accountNumber.equals(userDTO.accountNumber) : userDTO.accountNumber != null)
            return false;
        return currency != null ? currency.equals(userDTO.currency) : userDTO.currency == null;

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password_1 != null ? password_1.hashCode() : 0);
        result = 31 * result + (password_2 != null ? password_2.hashCode() : 0);
        result = 31 * result + (accountNumber != null ? accountNumber.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword_1() {
        return password_1;
    }

    public void setPassword_1(String password_1) {
        this.password_1 = password_1;
    }

    public String getPassword_2() {
        return password_2;
    }

    public void setPassword_2(String password_2) {
        this.password_2 = password_2;
    }
}
