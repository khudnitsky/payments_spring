package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.pojos.*;
import by.pvt.khudnitsky.payments.enums.AccountStatusType;
import by.pvt.khudnitsky.payments.services.IAccountService;
import by.pvt.khudnitsky.payments.services.IOperationService;
import by.pvt.khudnitsky.payments.services.IUserService;
import by.pvt.khudnitsky.payments.utils.EntityBuilder;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */

@ContextConfiguration("/test-services-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class OperationServiceImplTest {

    @Autowired
    private IUserService userService;
    @Autowired
    private IOperationService operationService;
    @Autowired
    private IAccountService accountService;

    private Operation expectedOperation;
    private Operation actualOperation;
    private User user;
    private Account account;
    private Serializable operationId;
    private Serializable userId;
    private Serializable accountId;

    @Before
    public void setUp() throws Exception {
        user = EntityBuilder.buildUser("TEST", "TEST", "TEST", "TEST", null, null, null);
        account = EntityBuilder.buildAccount(1000L, 200D, AccountStatusType.UNBLOCKED, null, user);
        user.addAccount(account);
        expectedOperation = EntityBuilder.buildOperation(200D, "TEST", Calendar.getInstance(), user, account);
        user.addOperation(expectedOperation);
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