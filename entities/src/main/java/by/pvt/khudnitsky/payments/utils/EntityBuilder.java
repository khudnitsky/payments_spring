/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.utils;

import by.pvt.khudnitsky.payments.pojos.*;
import by.pvt.khudnitsky.payments.enums.AccessLevelType;
import by.pvt.khudnitsky.payments.enums.AccountStatusType;
import by.pvt.khudnitsky.payments.enums.CurrencyType;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

/**
 * Utility class for pojos buildings
 * @author khudnitsky
 * @version 1.0
 */

public class EntityBuilder {
    private EntityBuilder(){}

    /**
     * Builds Account entity
     * @param accountNumber - account's number
     * @param deposit - account's deposit
     * @param accountStatus - account's status
     * @param currency - currency
     * @param user - user
     * @return Account entity
     */
    public static Account buildAccount(Long accountNumber, Double deposit, AccountStatusType accountStatus, Currency currency, User user){
        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setDeposit(deposit);
        account.setAccountStatus(accountStatus);
        account.setCurrency(currency);
        account.setUser(user);
        return account;
    }

    /**
     * Builds Operation entity
     * @param amount - the value of operation
     * @param description - description of operation
     * @param date - date
     * @param user - user
     * @param account - account
     * @return Operation entity
     */
    public static Operation buildOperation(Double amount, String description, Calendar date, User user, Account account){
        Operation operation = new Operation();
        operation.setAmount(amount);
        operation.setDescription(description);
        operation.setDate(date);
        operation.setUser(user);
        operation.setAccount(account);
        return operation;
    }

    /**
     * Builds Operation entity
     * @param user - user
     * @param description - description of operation
     * @param amount - the value of operation
     * @return
     */
    public static Operation buildOperation(User user, String description, Double amount){
        Operation operation = new Operation();
        operation.setUser(user);
        Set<Account> accounts = user.getAccounts();
        Account account = null;
        Iterator<Account> iterator = accounts.iterator();
        while (iterator.hasNext()){
            account = iterator.next();
        }
        operation.setAccount(account);
        operation.setAmount(amount);
        operation.setDescription(description);
        operation.setDate(Calendar.getInstance());
        return operation;
    }

    /**
     * Builds User entity
     * @param firstName - name of user
     * @param lastName - surname of user
     * @param login - user's login
     * @param password - user's password
     * @param userDetail - user's details
     * @param accounts - set of accounts
     * @param accessLevels - set of access levels
     * @return User entity
     */
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

    /**
     * Builds Currency entity
     * @param currencyType - value of enum CurrencyType
     * @return Currency entity
     */
    public static Currency buildCurrency(CurrencyType currencyType){
        Currency currency = new Currency();
        currency.setCurrencyType(currencyType);
        return currency;
    }

    /**
     * Builds UserDetail entity
     * @param address - address
     * @return UserDetail entity
     */
    public static UserDetail buildUserDetail(Address address){
        UserDetail userDetail = new UserDetail();
        userDetail.setAddress(address);
        return userDetail;
    }

    /**
     * Builds Address entity
     * @param city - city
     * @param street - street
     * @param zipCode - postal code
     * @return Address entity
     */
    public static Address buildAddress (String city, String street, String zipCode){
        Address address = new Address();
        address.setCity(city);
        address.setStreet(street);
        address.setZipCode(zipCode);
        return address;
    }

    /**
     * Builds AccessLevel entity
     * @param accessLevelType - value of enum AccessLevelType
     * @return AccessLevel entity
     */
    public static AccessLevel buildAccessLevel(AccessLevelType accessLevelType){
        AccessLevel accessLevel = new AccessLevel();
        accessLevel.setAccessLevelType(accessLevelType);
        return accessLevel;
    }
}
