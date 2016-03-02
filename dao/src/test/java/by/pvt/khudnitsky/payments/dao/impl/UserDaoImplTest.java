package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.dao.*;
import by.pvt.khudnitsky.payments.pojos.*;
import by.pvt.khudnitsky.payments.enums.AccessLevelType;
import by.pvt.khudnitsky.payments.enums.AccountStatusType;
import by.pvt.khudnitsky.payments.enums.CurrencyType;
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

@ContextConfiguration("/test-dao-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(transactionManager = "transactionManager")
public class UserDaoImplTest {

    @Autowired
    private IOperationDao operationDao;
    @Autowired
    private IAccountDao accountDao;
    @Autowired
    private ICurrencyDao currencyDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IUserDetailDao userDetailDao;
    @Autowired
    private IAccessLevelDao accessLevelDao;

    private User expectedUser;
    private User actualUser;
    private Operation operation;
    private UserDetail userDetail;
    private Account account;
    private Currency currency;
    private AccessLevel accessLevel;
    private Serializable operationId;
    private Serializable accountId;
    private Serializable userId;
    private Serializable currencyId;
    private Serializable accessLevelId;

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
    }

    @Test
    public void testSave() throws Exception {
        persistEntities();
        expectedUser.setId((Long) userId);
        actualUser = userDao.getById((Long) userId);
        Assert.assertEquals("save() method failed: ", expectedUser, actualUser);
    }

    @Ignore
    @Test
    public void testGetAll() throws Exception {
        Long expectedSize = (long) userDao.getAll().size();
        Long actualSize = userDao.getAmount(); // todo не будет работать, будет находить всех, переделать
        Assert.assertEquals("getAll() method failed: ", expectedSize, actualSize);
    }

    @Test
    public void testGetById() throws Exception {
        persistEntities();
        expectedUser.setId((Long) userId);
        actualUser = userDao.getById((Long) userId);
        Assert.assertEquals("getById() method failed: ", expectedUser, actualUser);
    }


    @Test
    public void testUpdate() throws Exception {
        persistEntities();
        expectedUser.setId((Long) userId);
        expectedUser.setFirstName("UPDATED");
        userDao.update(expectedUser);
        actualUser = userDao.getById((Long) userId);
        Assert.assertEquals("update() method failed: ", expectedUser, actualUser);
    }

    @Test
    public void testDelete() throws Exception {
        persistEntities();
        delete();
        actualUser = userDao.getById((Long) userId);
        Assert.assertNull("delete() method failed: ", actualUser);
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
        accountId = null;
        userId = null;
        currencyId = null;
    }

    private void persistEntities() throws Exception {
        userDetailDao.save(userDetail);
        accessLevelDao.save(accessLevel);
        currencyId = currencyDao.save(currency);
        userId = userDao.save(expectedUser);
        accountDao.save(account);
        operationId = operationDao.save(operation);
    }

    private void delete() throws Exception {
        operationDao.delete((Long) operationId);
        userDao.delete((Long) userId);
        currencyDao.delete((Long) currencyId);
    }
}