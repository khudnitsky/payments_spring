package by.pvt.khudnitsky.payments.services.impl;

import by.pvt.khudnitsky.payments.dao.impl.*;
import by.pvt.khudnitsky.payments.entities.*;
import by.pvt.khudnitsky.payments.enums.AccessLevelType;
import by.pvt.khudnitsky.payments.enums.AccountStatusType;
import by.pvt.khudnitsky.payments.enums.CurrencyType;
import by.pvt.khudnitsky.payments.utils.EntityBuilder;
import org.junit.*;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class UserServiceImplTest {
    private static UserServiceImpl userService;
    private static OperationServiceImpl operationService;
    private static CurrencyServiceImpl currencyService;
    private static AccessLevelServiceImpl accessLevelService;
    private static AccountServiceImpl accountService;
    private static UserDetailServiceImpl userDetailService;
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

    @BeforeClass
    public static void initTest(){
        userService = UserServiceImpl.getInstance();
        operationService = OperationServiceImpl.getInstance();
        currencyService = CurrencyServiceImpl.getInstance();
        accessLevelService = AccessLevelServiceImpl.getInstance();
        accountService = AccountServiceImpl.getInstance();
        userDetailService = UserDetailServiceImpl.getInstance();
    }

    @Before
    public void setUp() throws Exception {
        accessLevel = EntityBuilder.buildAccessLevel(AccessLevelType.CLIENT);
        Set<AccessLevel> accessLevels = new HashSet<>();
        accessLevels.add(accessLevel);

        Address address = EntityBuilder.buildAddress("TEST", "TEST", "TEST");
        userDetail = EntityBuilder.buildUserDetail(address);
        expectedUser = EntityBuilder.buildUser("TEST", "TEST", "TEST", "TEST", userDetail, null, null);
        Set<User> users = new HashSet<>();
        users.add(expectedUser);

        expectedUser.setAccessLevels(accessLevels);
        accessLevel.setUsers(users);

        currency = EntityBuilder.buildCurrency(CurrencyType.BYR);
        account = EntityBuilder.buildAccount(1000L, 200D, AccountStatusType.UNBLOCKED, currency, expectedUser);
        Set<Account> accounts = new HashSet<>();
        accounts.add(account);
        expectedUser.setAccounts(accounts);

        operation = EntityBuilder.buildOperation(200D, "TEST", Calendar.getInstance(), expectedUser, account);
        Set<Operation> operations = new HashSet<>();
        expectedUser.setOperations(operations);
        persistEntities();
    }

    @Test
    public void testSave() throws Exception {
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
        expectedUser.setId((Long) userId);
        actualUser = userService.getById((Long) userId);
        delete();
        Assert.assertEquals("getById() method failed: ", expectedUser, actualUser);
    }

    @Test
    public void testUpdate() throws Exception {
        expectedUser.setId((Long) userId);
        expectedUser.setFirstName("UPDATED");
        userService.update(expectedUser);
        actualUser = userService.getById((Long) userId);
        delete();
        Assert.assertEquals("update() method failed: ", expectedUser, actualUser);
    }

    @Test
    public void testDelete() throws Exception {
        delete();
        actualUser = userService.getById((Long) userId);
        Assert.assertNull("delete() method failed: ", actualUser);
    }

    @Test
    public void testCheckUserAuthorization() throws Exception {
        Boolean expected = true;
        Boolean actual = userService.checkUserAuthorization(expectedUser.getLogin(), expectedUser.getPassword());
        delete();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetUserByLogin() throws Exception {
        actualUser = userService.getUserByLogin(expectedUser.getLogin());
        delete();
        Assert.assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testCheckAccessLevel() throws Exception {
        AccessLevelType actual = userService.checkAccessLevel(expectedUser);
        delete();
        Assert.assertEquals(accessLevel.getAccessLevelType(), (actual));
    }

    @Test
    public void testCheckIsNewUser() throws Exception {
        boolean expected = false;
        boolean actual = userService.checkIsNewUser(expectedUser);
        delete();
        Assert.assertEquals(new Boolean(expected), new Boolean(actual));
    }

    @Test
    public void testBookUser() throws Exception {
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
    }

    @AfterClass
    public static void closeTest() throws Exception{
        operationService = null;
        accountService = null;
        currencyService = null;
        userService = null;
        userDetailService = null;
        accessLevelService = null;
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