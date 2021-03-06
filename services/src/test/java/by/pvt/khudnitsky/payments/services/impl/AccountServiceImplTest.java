package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.pojos.*;
import by.pvt.khudnitsky.payments.enums.AccountStatusType;
import by.pvt.khudnitsky.payments.enums.CurrencyType;
import by.pvt.khudnitsky.payments.services.IAccountService;
import by.pvt.khudnitsky.payments.services.ICurrencyService;
import by.pvt.khudnitsky.payments.services.IOperationService;
import by.pvt.khudnitsky.payments.services.IUserService;
import by.pvt.khudnitsky.payments.utils.EntityBuilder;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.Serializable;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */

@ContextConfiguration("/test-services-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountServiceImplTest {

    @Autowired
    private IUserService userService;
    @Autowired
    private ICurrencyService currencyService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IOperationService operationService;

    private Account expectedAccount;
    private Account actualAccount;
    private User user;
    private Operation operation;
    private Currency currency;
    private Serializable userId;
    private Serializable currencyId;
    private Serializable accountId;
    private Long accountNumber;

    @Before
    public void setUp() throws Exception {
        user = EntityBuilder.buildUser("TEST", "TEST", "TEST", "TEST", null, null, null);
        currency = EntityBuilder.buildCurrency(CurrencyType.BYR);
        accountNumber = 1000L;
        expectedAccount = EntityBuilder.buildAccount(accountNumber, 200D, AccountStatusType.UNBLOCKED, currency, user);
        user.addAccount(expectedAccount);
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

    @Ignore
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
        accountService.updateAccountStatus(accountNumber, AccountStatusType.BLOCKED);
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
        accountNumber = null;
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