package by.pvt.khudnitsky.payments.dao.impl;

import by.pvt.khudnitsky.payments.dao.IAccountDao;
import by.pvt.khudnitsky.payments.dao.ICurrencyDao;
import by.pvt.khudnitsky.payments.dao.IUserDao;
import by.pvt.khudnitsky.payments.dao.IUserDetailDao;
import by.pvt.khudnitsky.payments.entities.*;
import by.pvt.khudnitsky.payments.enums.AccountStatusType;
import by.pvt.khudnitsky.payments.enums.CurrencyType;
import by.pvt.khudnitsky.payments.utils.EntityBuilder;
import by.pvt.khudnitsky.payments.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */

@ContextConfiguration("/test-dao-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(transactionManager = "transactionManager")
//@Rollback(value = false)
public class AccountDaoImplTest {

    @Autowired
    private IAccountDao accountDao;
    @Autowired
    private ICurrencyDao currencyDao;
    @Autowired
    private IUserDao userDao;
    @Autowired
    private IUserDetailDao userDetailDao;

    private Account expectedAccount;
    private Account actualAccount;
    private User user;
    private UserDetail userDetail;
    private Currency currency;
    private Serializable accountId;
    private Serializable userId;
    private Serializable currencyId;

    @Before
    public void setUp(){
        Address address = EntityBuilder.buildAddress("TEST", "TEST", "TEST");
        userDetail = EntityBuilder.buildUserDetail(address);
        user = EntityBuilder.buildUser("TEST", "TEST", "TEST", "TEST", userDetail, null, null);
        currency = EntityBuilder.buildCurrency(CurrencyType.BYR);
        expectedAccount = EntityBuilder.buildAccount(1000L, 200D, AccountStatusType.UNBLOCKED, currency, user);
    }

    @Test
    public void testSave() throws Exception{
        persistEntities();
        expectedAccount.setId((Long) accountId);
        actualAccount = accountDao.getById((Long) accountId);
        Assert.assertEquals("save() method failed: ", expectedAccount, actualAccount);
    }

    @Test
    public void testGetAll() throws Exception {
        Long expectedSize = (long) accountDao.getAll().size();
        Long actualSize = accountDao.getAmount();
        Assert.assertEquals("getAll() method failed: ", expectedSize, actualSize);
    }

    @Test
    public void testGetById() throws Exception {
        persistEntities();
        expectedAccount.setId((Long) accountId);
        actualAccount = accountDao.getById((Long) accountId);
        Assert.assertEquals("getById() method failed: ", expectedAccount, actualAccount);
    }


    @Test
    public void testUpdate() throws Exception {
        persistEntities();
        expectedAccount.setId((Long) accountId);
        expectedAccount.setDeposit(3000D);
        accountDao.update(expectedAccount);
        actualAccount = accountDao.getById((Long) accountId);
        Assert.assertEquals("update() method failed: ", expectedAccount, actualAccount);
    }

    @Test
    public void testDelete() throws Exception {
        persistEntities();
        delete();
        actualAccount = accountDao.getById((Long) accountId);
        Assert.assertNull("delete() method failed: ", actualAccount);
    }

    @Test
    public void testIsAccountStatusBlocked() throws Exception {
        Boolean expected;
        Boolean actual;

        expectedAccount.setAccountStatus(AccountStatusType.UNBLOCKED);
        persistEntities();
        expected = false;
        actual = accountDao.isAccountStatusBlocked((Long) accountId);
        Assert.assertEquals("isAccountStatusBlocked() method failed: ", expected, actual);

        expectedAccount.setAccountStatus(AccountStatusType.BLOCKED);
        accountDao.update(expectedAccount);
        expected = true;
        actual = accountDao.isAccountStatusBlocked((Long) accountId);
        Assert.assertEquals("isAccountStatusBlocked() method failed: ", expected, actual);
    }

    @Ignore
    @Test
    public void testGetBlockedAccounts() throws Exception {
        //TODO доделать
        expectedAccount.setAccountStatus(AccountStatusType.BLOCKED);
        persistEntities();
        Long expectedSize = (long) accountDao.getBlockedAccounts().size();
        Long actualSize = accountDao.getAmount();
        Assert.assertEquals("getAll() method failed: ", expectedSize, actualSize);
    }

    @After
    public void tearDown() throws Exception{
        expectedAccount = null;
        actualAccount = null;
        user = null;
        userDetail = null;
        currency = null;
        accountId = null;
        userId = null;
        currencyId = null;
    }

    private void persistEntities() throws Exception {
        userDetailDao.save(userDetail);
        userId = userDao.save(user);
        currencyId = currencyDao.save(currency);
        accountId = accountDao.save(expectedAccount);
    }

    private void delete() throws by.pvt.khudnitsky.payments.exceptions.DaoException {
        accountDao.delete((Long) accountId);
        userDao.delete((Long) userId);
        currencyDao.delete((Long) currencyId);
    }
}