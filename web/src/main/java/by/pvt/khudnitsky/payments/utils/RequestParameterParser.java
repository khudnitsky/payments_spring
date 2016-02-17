package by.pvt.khudnitsky.payments.utils;

import by.pvt.khudnitsky.payments.commands.factory.CommandType;
import by.pvt.khudnitsky.payments.entities.AccessLevel;
import by.pvt.khudnitsky.payments.entities.Currency;
import by.pvt.khudnitsky.payments.enums.AccountStatusType;
import by.pvt.khudnitsky.payments.enums.CurrencyType;
import by.pvt.khudnitsky.payments.enums.Parameters;
import by.pvt.khudnitsky.payments.enums.AccessLevelType;
import by.pvt.khudnitsky.payments.entities.Account;
import by.pvt.khudnitsky.payments.entities.User;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class RequestParameterParser {
    private RequestParameterParser() {}

    public static User getUser(HttpServletRequest request){
        AccessLevelType accessLevel;
        Set<AccessLevel> accessLevels = new HashSet<>();
        if (request.getParameter(Parameters.USER_ACCESS_LEVEL) != null){
            accessLevel = AccessLevelType.valueOf(request.getParameter(Parameters.USER_ACCESS_LEVEL).toUpperCase());
            AccessLevel access = new AccessLevel();
            access.setAccessLevelType(accessLevel);
            accessLevels.add(access);
        }
        String firstName = request.getParameter(Parameters.USER_FIRST_NAME);
        String lastName = request.getParameter(Parameters.USER_LAST_NAME);
        String login = request.getParameter(Parameters.USER_LOGIN);
        String password = request.getParameter(Parameters.USER_PASSWORD);

        User user = EntityBuilder.buildUser(firstName, lastName, login, password, null, null, accessLevels);
        return user;
    }

    public static Account getAccount(HttpServletRequest request) throws NumberFormatException {
        Long accountNumber = 0L;
        if (request.getParameter(Parameters.ACCOUNT_NUMBER) != null){
            accountNumber = Long.valueOf(request.getParameter(Parameters.ACCOUNT_NUMBER));
        }

        Currency currency = new Currency();
        if(request.getParameter(Parameters.ACCOUNT_CURRENCY) != null){
            currency.setCurrencyType(CurrencyType.valueOf(request.getParameter(Parameters.ACCOUNT_CURRENCY)));
        }

        Double deposit = 0D;
        if(request.getParameter(Parameters.AMOUNT) != null){
            deposit = Double.valueOf(request.getParameter(Parameters.AMOUNT));
        }

        AccountStatusType accountStatus = AccountStatusType.UNBLOCKED;
        if (request.getParameter(Parameters.ACCOUNT_STATUS) != null){
            accountStatus = AccountStatusType.valueOf(request.getParameter(Parameters.ACCOUNT_STATUS).toUpperCase());
        }

        Account account = EntityBuilder.buildAccount(accountNumber, deposit, accountStatus, currency, null);
        return account;
    }

    public static AccessLevelType getUserType(HttpServletRequest request) {
        return (AccessLevelType) request.getSession().getAttribute(Parameters.USERTYPE);
    }

    public static List<Account> getAccountsList(HttpServletRequest request) {
        return (List<Account>) request.getSession().getAttribute(Parameters.ACCOUNTS_LIST);
    }

    public static User getRecordUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(Parameters.USER);
    }

    public static CommandType getCommandType(HttpServletRequest request){
        String commandName = request.getParameter(Parameters.COMMAND);
        CommandType commandType = CommandType.LOGIN;
        if(commandName != null) {
            commandType = CommandType.valueOf(commandName.toUpperCase());
        }
        return commandType;
    }

    public static double getAmountFromFunds(HttpServletRequest request) throws NumberFormatException{
        return Double.valueOf(request.getParameter(Parameters.OPERATION_ADD_FUNDS));
    }

    public static double getAmountFromPayment(HttpServletRequest request) throws NumberFormatException{
        return Double.valueOf(request.getParameter(Parameters.OPERATION_PAYMENT));
    }
}
