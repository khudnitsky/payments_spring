/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.utils;

import by.pvt.khudnitsky.payments.entities.*;
import by.pvt.khudnitsky.payments.enums.AccessLevelType;
import by.pvt.khudnitsky.payments.enums.AccountStatusType;
import by.pvt.khudnitsky.payments.enums.CurrencyType;

import java.util.Calendar;
import java.util.Set;

/**
 * Utility class for entities buildings
 * @author khudnitsky
 * @version 1.0
 */

public class EntityBuilder {
    private EntityBuilder(){}

    public static Account buildAccount(Long accountNumber, Double deposit, AccountStatusType accountStatus, Currency currency, User user){
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setDeposit(deposit);
        account.setAccountStatus(accountStatus);
        account.setCurrency(currency);
        account.setUser(user);
        return account;
    }

    public static Operation buildOperation(Double amount, String description, Calendar date, User user, Account account){
        Operation operation = new Operation();
        operation.setAmount(amount);
        operation.setDescription(description);
        operation.setDate(date);
        operation.setUser(user);
        operation.setAccount(account);
        return operation;
    }

    public static User buildUser(String firstName, String lastName, String login, String password, UserDetail userDetail, Set<Account> accounts, Set<AccessLevel> accessLevels){
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setLogin(login);
        user.setPassword(password);
        user.setUserDetail(userDetail);
        user.setAccounts(accounts);
        user.setAccessLevels(accessLevels);
        return user;
    }

    /**
     * Creates card
     * NOT REALISED
     * @return throw UnsupportedOperationException()
     */
    public static Card buildCard(){
        throw new UnsupportedOperationException();
    }

    public static Currency buildCurrency(CurrencyType currencyType){
        Currency currency = new Currency();
        currency.setCurrencyType(currencyType);
        return currency;
    }

    public static UserDetail buildUserDetail(Address address){
        UserDetail userDetail = new UserDetail();
        userDetail.setAddress(address);
        return userDetail;
    }

    public static Address buildAddress (String city, String street, String zipCode){
        Address address = new Address();
        address.setCity(city);
        address.setStreet(street);
        address.setZipCode(zipCode);
        return address;
    }

    public static AccessLevel buildAccessLevel(AccessLevelType accessLevelType){
        AccessLevel accessLevel = new AccessLevel();
        accessLevel.setAccessLevelType(accessLevelType);
        return accessLevel;
    }
}
