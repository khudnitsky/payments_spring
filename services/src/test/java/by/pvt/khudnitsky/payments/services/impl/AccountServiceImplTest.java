package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.entities.*;
import by.pvt.khudnitsky.payments.enums.AccessLevelType;
import by.pvt.khudnitsky.payments.enums.AccountStatusType;
import by.pvt.khudnitsky.payments.dao.impl.AccountDaoImpl;
import by.pvt.khudnitsky.payments.enums.CurrencyType;
import by.pvt.khudnitsky.payments.utils.EntityBuilder;
import by.pvt.khudnitsky.payments.dao.impl.UserDaoImpl;
import org.junit.*;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class AccountServiceImplTest {
    private static UserServiceImpl userService;
    private static CurrencyServiceImpl currencyService;
    private static AccountServiceImpl accountService;
    private static OperationServiceImpl operationService;
    private Account expectedAccount;
    private Account actualAccount;
    private User user;
    private Operation operation;
    private Currency currency;
    private Serializable userId;
    private Serializable currencyId;
    private Serializable accountId;

    @BeforeClass
    public static void initTest(){
        userService = UserServiceImpl.getInstance();
        currencyService = CurrencyServiceImpl.getInstance();
        accountService = AccountServiceImpl.getInstance();
        operationService = OperationServiceImpl.getInstance();
    }

    @Before
    public void setUp() throws Exception {
        user = EntityBuilder.buildUser("TEST", "TEST", "TEST", "TEST", null, null, null);
        currency = EntityBuilder.buildCurrency(CurrencyType.BYR);
        expectedAccount = EntityBuilder.buildAccount(1000L, 200D, AccountStatusType.UNBLOCKED, currency, user);
        Set<Account> accounts = new HashSet<>();
        accounts.add(expectedAccount);
        user.setAccounts(accounts);
        persistEntities();
    }

    @Test
    public void testSave() throws Exception {
        expectedAccount.setId((Long) accountId);
        actualAccount = accountService.getById((Long) accountId);
        delete();
        Assert.assertEquals("save() method failed: ", expectedAccount, actualAccount);
    }

    @Test
    public void testGetById() throws Exception {
        expectedAccount.setId((Long) accountId);
        actualAccount = accountService.getById((Long) accountId);
        delete();
        Assert.assertEquals("getById() method failed: ", expectedAccount, actualAccount);
    }

    @Test
    public void testAddFunds() throws Exception {
        user.setId((Long) userId);
        Double amount = 200D;
        Double expected = expectedAccount.getDeposit() + amount;
        accountService.addFunds(user, "TEST", amount);
        Double actual = accountService.getById((Long) accountId).getDeposit();
        operationService.deleteByAccountId((Long) accountId);
        userService.delete((Long) userId);
        currencyService.delete((Long) currencyId);
        Assert.assertEquals(expected, actual, 0.01);
    }

    @Test
    public void testPayment() throws Exception {
        user.setId((Long) userId);
        Double amount = 200D;
        Double expected = expectedAccount.getDeposit() - amount;
        accountService.payment(user, "TEST", amount);
        Double actual = accountService.getById((Long) accountId).getDeposit();
        operationService.deleteByAccountId((Long) accountId);
        userService.delete((Long) userId);
        currencyService.delete((Long) currencyId);
        Assert.assertEquals(expected, actual, 0.01);
    }

    @Test
    public void testBlockAccount() throws Exception {
        user.setId((Long) userId);
        accountService.blockAccount(user, "TEST");
        AccountStatusType expected = AccountStatusType.BLOCKED;
        AccountStatusType actual = accountService.getById((Long) accountId).getAccountStatus();
        operationService.deleteByAccountId((Long) accountId);
        userService.delete((Long) userId);
        currencyService.delete((Long) currencyId);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testUpdateAccountStatus() throws Exception {
        user.setId((Long) userId);
        accountService.updateAccountStatus((Long) accountId, AccountStatusType.BLOCKED);
        AccountStatusType expected = AccountStatusType.BLOCKED;
        AccountStatusType actual = accountService.getById((Long) accountId).getAccountStatus();
        delete();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testCheckAccountStatus() throws Exception {
        user.setId((Long) userId);
        Boolean expected = false; // UNBLOCKED
        Boolean actual = accountService.checkAccountStatus((Long) accountId);
        delete();
        Assert.assertEquals(expected, actual);
    }

    @Ignore
    @Test
    public void testGetBlockedAccounts() throws Exception {

    }

    @After
    public void tearDown() throws Exception{
        expectedAccount = null;
        actualAccount = null;
        user = null;
        operation = null;
        currency = null;
        userId = null;
        currencyId = null;
        accountId = null;
    }

    @AfterClass
    public static void closeTest() throws Exception{
        accountService = null;
        currencyService = null;
        userService = null;
        operationService = null;
    }

    private void persistEntities() throws Exception {
        currencyId = currencyService.save(currency);
        userId = userService.save(user);
        accountId = accountService.save(expectedAccount);
    }

    private void delete() throws Exception {
        //accountService.delete((Long) accountId);
        userService.delete((Long) userId);
        currencyService.delete((Long) currencyId);
    }
}