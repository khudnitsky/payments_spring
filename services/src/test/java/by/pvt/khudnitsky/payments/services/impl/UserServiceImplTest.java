package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.dao.impl.*;
import by.pvt.khudnitsky.payments.entities.*;
import by.pvt.khudnitsky.payments.enums.AccessLevelType;
import by.pvt.khudnitsky.payments.enums.AccountStatusType;
import by.pvt.khudnitsky.payments.enums.CurrencyType;
import by.pvt.khudnitsky.payments.services.*;
import by.pvt.khudnitsky.payments.utils.EntityBuilder;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */

@ContextConfiguration("/test-services-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {

    @Autowired
    private IUserService userService;
    @Autowired
    private IOperationService operationService;
    @Autowired
    private ICurrencyService currencyService;
    @Autowired
    private IAccessLevelService accessLevelService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IUserDetailService userDetailService;

    private User expectedUser;
    private User actualUser;
    private Operation operation;
    private UserDetail userDetail;
    private Account account;
    private Currency currency;
    private AccessLevel accessLevel;
    private Serializable operationId;
    private Serializable userId;
    private Serializable currencyId;
    private Long accountNumber;

    @Before
    public void setUp() throws Exception {
        accessLevel = EntityBuilder.buildAccessLevel(AccessLevelType.CLIENT);

        Address address = EntityBuilder.buildAddress("TEST", "TEST", "TEST");
        userDetail = EntityBuilder.buildUserDetail(address);
        expectedUser = EntityBuilder.buildUser("TEST", "TEST", "TEST", "TEST", userDetail, null, null);

        accessLevel.addUser(expectedUser);
        expectedUser.addAccessLevel(accessLevel);

        currency = EntityBuilder.buildCurrency(CurrencyType.BYR);
        accountNumber = 1000L;
        account = EntityBuilder.buildAccount(accountNumber, 200D, AccountStatusType.UNBLOCKED, currency, expectedUser);

        expectedUser.addAccount(account);

        operation = EntityBuilder.buildOperation(200D, "TEST", Calendar.getInstance(), expectedUser, account);
        expectedUser.addOperation(operation);
    }

    @Test
    public void testSave() throws Exception {
        persistEntities();
        expectedUser.setId((Long) userId);
        actualUser = userService.getById((Long) userId);
        delete();
        Assert.assertEquals("save() method failed: ", expectedUser, actualUser);
    }

    @Ignore
    @Test
    public void testGetAll() throws Exception {

    }

    @Test
    public void testGetById() throws Exception {
        persistEntities();
        expectedUser.setId((Long) userId);
        actualUser = userService.getById((Long) userId);
        delete();
        Assert.assertEquals("getById() method failed: ", expectedUser, actualUser);
    }

    @Test
    public void testUpdate() throws Exception {
        persistEntities();
        expectedUser.setId((Long) userId);
        expectedUser.setFirstName("UPDATED");
        userService.update(expectedUser);
        actualUser = userService.getById((Long) userId);
        delete();
        Assert.assertEquals("update() method failed: ", expectedUser, actualUser);
    }

    @Test
    public void testDelete() throws Exception {
        persistEntities();
        delete();
        actualUser = userService.getById((Long) userId);
        Assert.assertNull("delete() method failed: ", actualUser);
    }

    @Test
    public void testCheckUserAuthorization() throws Exception {
        persistEntities();
        Boolean expected = true;
        Boolean actual = userService.checkUserAuthorization(expectedUser.getLogin(), expectedUser.getPassword());
        delete();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetUserByLogin() throws Exception {
        persistEntities();
        actualUser = userService.getUserByLogin(expectedUser.getLogin());
        delete();
        Assert.assertEquals(expectedUser, actualUser);
    }

//    @Test
//    public void testCheckAccessLevel() throws Exception {
//        AccessLevelType actual = userService.checkAccessLevel(expectedUser);
//        delete();
//        Assert.assertEquals(accessLevel.getAccessLevelType(), (actual));
//    }

    @Test
    public void testCheckIsNewUser() throws Exception {
        persistEntities();
        boolean expected = false;
        boolean actual = userService.checkIsNewUser(expectedUser.getLogin(), accountNumber);
        delete();
        Assert.assertEquals(new Boolean(expected), new Boolean(actual));
    }

    @Ignore
    @Test
    public void testBookUser() throws Exception {
        accessLevel = EntityBuilder.buildAccessLevel(AccessLevelType.CLIENT);
        currency = EntityBuilder.buildCurrency(CurrencyType.BYR);
        accessLevelService.save(accessLevel);
        currencyService.save(currency);
        userService.bookUser(expectedUser, account);
        expectedUser.setId((Long) userId);
        actualUser = userService.getUserByLogin(expectedUser.getLogin());
        delete();
        Assert.assertEquals(expectedUser, actualUser);
    }

    @After
    public void tearDown() throws Exception{
        expectedUser = null;
        actualUser = null;
        account = null;
        operation = null;
        accessLevel = null;
        userDetail = null;
        currency = null;
        operationId = null;
        userId = null;
        currencyId = null;
        accountNumber = null;
    }

    private void persistEntities() throws Exception {
        userDetailService.save(userDetail);
        accessLevelService.save(accessLevel);
        currencyId = currencyService.save(currency);
        userId = userService.save(expectedUser);
        accountService.save(account);
        operationId = operationService.save(operation);
    }

    private void delete() throws Exception {
        operationService.delete((Long) operationId);
        userService.delete((Long) userId);
        currencyService.delete((Long) currencyId);
    }
}