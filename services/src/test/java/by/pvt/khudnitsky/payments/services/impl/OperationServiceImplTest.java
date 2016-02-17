package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.dao.impl.AccountDaoImpl;
import by.pvt.khudnitsky.payments.entities.*;
import by.pvt.khudnitsky.payments.enums.AccessLevelType;
import by.pvt.khudnitsky.payments.enums.AccountStatusType;
import by.pvt.khudnitsky.payments.enums.CurrencyType;
import by.pvt.khudnitsky.payments.utils.EntityBuilder;
import by.pvt.khudnitsky.payments.dao.impl.OperationDaoImpl;
import by.pvt.khudnitsky.payments.dao.impl.UserDaoImpl;
import org.junit.*;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class OperationServiceImplTest {
    private static UserServiceImpl userService;
    private static OperationServiceImpl operationService;
    private static AccountServiceImpl accountService;
    private Operation expectedOperation;
    private Operation actualOperation;
    private User user;
    private Account account;
    private Serializable operationId;
    private Serializable userId;
    private Serializable accountId;

    @BeforeClass
    public static void initTest(){
        userService = UserServiceImpl.getInstance();
        operationService = OperationServiceImpl.getInstance();
        accountService = AccountServiceImpl.getInstance();
    }

    @Before
    public void setUp() throws Exception {
        user = EntityBuilder.buildUser("TEST", "TEST", "TEST", "TEST", null, null, null);
        account = EntityBuilder.buildAccount(1000L, 200D, AccountStatusType.UNBLOCKED, null, user);
        Set<Account> accounts = new HashSet<>();
        accounts.add(account);
        user.setAccounts(accounts);
        expectedOperation = EntityBuilder.buildOperation(200D, "TEST", Calendar.getInstance(), user, account);
        Set<Operation> operations = new HashSet<>();
        user.setOperations(operations);
        persistEntities();
    }

    @Test
    public void testSave() throws Exception {
        expectedOperation.setId((Long) operationId);
        actualOperation = operationService.getById((Long) operationId);
        delete();
        Assert.assertEquals("save() method failed: ", expectedOperation, actualOperation);
    }

    @Test
    public void testGetById() throws Exception{
        expectedOperation.setId((Long) operationId);
        actualOperation = operationService.getById((Long) operationId);
        delete();
        Assert.assertEquals("getById() method failed: ", expectedOperation, actualOperation);
    }

    @Test
    public void testDelete() throws Exception {
        delete();
        actualOperation = operationService.getById((Long) operationId);
        Assert.assertNull("delete() method failed: ", actualOperation);
    }

    @Ignore
    @Test
    public void testDeleteByAccountId() throws Exception {
        // TODO Session flush
        operationService.deleteByAccountId((Long) accountId);
        actualOperation = operationService.getById((Long) operationId);
        Assert.assertNull("delete() method failed: ", actualOperation);
        accountService.delete((Long) accountId);
        userService.delete((Long) userId);
    }

    @After
    public void tearDown() throws Exception{
        expectedOperation = null;
        actualOperation = null;
        account = null;
        user = null;
        operationId = null;
        userId = null;
        accountId = null;
    }

    @AfterClass
    public static void closeTest() throws Exception{
        operationService = null;
        accountService = null;
        userService = null;
    }

    private void persistEntities() throws Exception {
        userId = userService.save(user);
        accountId = accountService.save(account);
        operationId = operationService.save(expectedOperation);
    }

    private void delete() throws Exception {
        operationService.delete((Long) operationId);
        userService.delete((Long) userId);
    }
}