package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pl.jakubpiecuch.trainingmanager.BaseIntegrationTestCase;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.service.repository.account.AccountCriteria;
import pl.jakubpiecuch.trainingmanager.service.user.model.Provider;
import pl.jakubpiecuch.trainingmanager.service.user.social.SocialProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class AccountDaoImplTest extends BaseIntegrationTestCase {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    private static final Long ID = 1l;
    private static final String NAME = "test.user";
    private static final String PASSWORD = "f0e734ab8910dee9762d0ee07964288dd8ffd95be9ab646af02ba1c1256e5037";
    private static final String SALT = "3994c7aea794c1cf";
    private static final String CREATED_STRING = "2014-12-07 13:52:56.805";
    private static final Long NOT_EXISTS_ID = 99l;
    private static final String ROLE_NAME = "ADMIN";
    private static Date CREATED;
    private static final String UPDATED_STRING = "2014-12-07 13:53:16.062";
    private static Date UPDATED;
    private static final Account.Status STATUS = Account.Status.ACTIVE;
    private static final String EMAIL = "test.user@test.com";
    private static final String FIRST_NAME = "Test";
    private static final String LAST_NAME = "User";
    private static final String CONFIG = "{\"firstName\":\""+FIRST_NAME+"\",\"lastName\":\""+LAST_NAME+"\"}";


    static {
        try {
            CREATED = FORMAT.parse(CREATED_STRING);
            UPDATED = FORMAT.parse(UPDATED_STRING);
        } catch (ParseException e) {}
    }

    @Autowired
    private RepoDao<Account, AccountCriteria> accountDao;

    @Test
    @Transactional
    public void testDelete() {
        Account account = new Account();
        account.setName(NAME + "delete");
        account.setSalt(SALT);
        account.setPassword(PASSWORD);
        account.setStatus(Account.Status.CREATED);
        account.setProvider(Provider.Type.LOCAL);
        account.setSocialType(SocialProvider.SocialType.NONE);
        accountDao.create(account);
        accountDao.flush();
        assertNotNull(account.getId());
        accountDao.delete(account);
        assertNull(accountDao.findById(account.getId()));
    }

    @Test
    @Transactional
    public void testUpdate() {
        Account account = new Account();
        account.setName(NAME + "update");
        account.setSalt(SALT);
        account.setPassword(PASSWORD);
        account.setStatus(Account.Status.CREATED);
        account.setProvider(Provider.Type.LOCAL);
        account.setSocialType(SocialProvider.SocialType.NONE);
        accountDao.create(account);
        accountDao.flush();
        assertEquals(0l, accountDao.findByCriteria(new AccountCriteria().addEmailRestrictions("email")).getCount());
        account.setEmail("email");
        accountDao.update(account);
        assertEquals(1l, accountDao.findByCriteria(new AccountCriteria().addEmailRestrictions("email")).getCount());
    }

    @Test
    @Transactional
    public void testSave() {
        Account account = new Account();
        account.setName(NAME + "new");
        account.setSalt(SALT);
        account.setPassword(PASSWORD);
        account.setStatus(Account.Status.CREATED);
        account.setProvider(Provider.Type.LOCAL);
        account.setSocialType(SocialProvider.SocialType.NONE);
        accountDao.create(account);
        accountDao.flush();
        assertNotNull(account.getId());
        assertNotNull(account.getCreated());

        account.setStatus(Account.Status.ACTIVE);
        accountDao.create(account);
        accountDao.flush();
        assertNotNull(account.getUpdated());

        boolean exFlag = false;
        try {
            account.setStatus(Account.Status.ACTIVE);
            account.setId(NOT_EXISTS_ID);
            accountDao.create(account);
            accountDao.flush();
        } catch(HibernateException ex) {
            exFlag = true;
        }
        assertTrue(exFlag);
    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void testSaveNull() {
        accountDao.create(null);
    }

    @Test
    @Transactional
    public void testSaveValidity() {
        int counter = 0;

        //null name
        try {
            Account account = new Account();
            account.setEmail(EMAIL);
            accountDao.create(account);
        } catch (ConstraintViolationException ex) {
            counter++; //1
        }

        //null password
        try {
            Account account = new Account();
            account.setEmail(EMAIL);
            account.setName(NAME);
            accountDao.create(account);
        } catch (ConstraintViolationException ex) {
            counter++; //2
        }

        //null salt
        try {
            Account account = new Account();
            account.setEmail(EMAIL);
            account.setName(NAME);
            account.setPassword(PASSWORD);
            accountDao.create(account);
        } catch (ConstraintViolationException ex) {
            counter++; //3
        }

        //null status
        try {
            Account account = new Account();
            account.setEmail(EMAIL);
            account.setName(NAME);
            account.setPassword(PASSWORD);
            account.setSalt(SALT);
            accountDao.create(account);
        } catch (ConstraintViolationException ex) {
            counter++; //4
        }

        //null provider
        try {
            Account account = new Account();
            account.setEmail(EMAIL);
            account.setName(NAME);
            account.setPassword(PASSWORD);
            account.setSalt(SALT);
            account.setStatus(STATUS);
            accountDao.create(account);
        } catch (ConstraintViolationException ex) {
            counter++; //5
        }

        //null social type
        try {
            Account account = new Account();
            account.setEmail(EMAIL);
            account.setName(NAME);
            account.setPassword(PASSWORD);
            account.setSalt(SALT);
            account.setStatus(STATUS);
            account.setProvider(Provider.Type.LOCAL);
            accountDao.create(account);
        } catch (ConstraintViolationException ex) {
            counter++; //6
        }

        //not unique name + social type
        try {
            Account account = new Account();
            account.setEmail(EMAIL);
            account.setName(NAME);
            account.setPassword(PASSWORD);
            account.setSalt(SALT);
            account.setStatus(STATUS);
            account.setProvider(Provider.Type.LOCAL);
            account.setSocialType(SocialProvider.SocialType.NONE);
            accountDao.create(account);
        } catch (ConstraintViolationException ex) {
            counter++; //7
        }

        //not unique email + social type
        try {
        Account account = new Account();
        account.setEmail(EMAIL);
        account.setName(NAME + "unique_name");
        account.setPassword(PASSWORD);
        account.setSalt(SALT);
        account.setStatus(STATUS);
        account.setProvider(Provider.Type.LOCAL);
        account.setSocialType(SocialProvider.SocialType.NONE);
        accountDao.create(account);
        } catch (ConstraintViolationException ex) {
            counter++; //8
        }

        //unique social type
        Account account = new Account();
        account.setEmail(EMAIL);
        account.setName(NAME);
        account.setPassword(PASSWORD);
        account.setSalt(SALT);
        account.setStatus(STATUS);
        account.setProvider(Provider.Type.LOCAL);
        account.setSocialType(SocialProvider.SocialType.FACEBOOK);
        accountDao.create(account);

        assertNotNull(account.getId());
        assertNotNull(account.getCreated());
        assertEquals(8, counter);
    }

    @Test
    public void testFindByCriteria() {
        PageResult<Account> result = accountDao.findByCriteria(null);

        assertEquals(0l, result.getCount());

        result = accountDao.findByCriteria(new AccountCriteria());

        assertEquals(1l, result.getCount());
        assertAccount(result.getResult().get(0));

        result = accountDao.findByCriteria(new AccountCriteria().addEmailRestrictions("wrong@email.com"));

        assertEquals(0l, result.getCount());

        result = accountDao.findByCriteria(new AccountCriteria().addEmailRestrictions(EMAIL));

        assertEquals(1l, result.getCount());
        assertAccount(result.getResult().get(0));

        result = accountDao.findByCriteria(new AccountCriteria().addEmailRestrictions(EMAIL).addNameRestrictions("wrong.name"));

        assertEquals(0l, result.getCount());

        result = accountDao.findByCriteria(new AccountCriteria().addEmailRestrictions(EMAIL).addNameRestrictions(NAME));

        assertEquals(1l, result.getCount());
        assertAccount(result.getResult().get(0));
    }

    private static void assertAccount(Account account) {
        assertEquals(ID, account.getId());
        assertEquals(PASSWORD, account.getPassword());
        assertEquals(NAME, account.getName());
        assertEquals(SALT, account.getSalt());
        assertEquals(UPDATED, account.getUpdated());
        assertEquals(CREATED, account.getCreated());
        assertEquals(STATUS, account.getStatus());
        assertEquals(EMAIL, account.getEmail());
        assertEquals(CONFIG, account.getConfig());
        assertNotNull(account.getRoles());
        assertEquals(ROLE_NAME, account.getRoles().get(0).getName());
    }
}