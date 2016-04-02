package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pl.jakubpiecuch.trainingmanager.BaseUnitDaoTestCase;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.domain.Permissions;
import pl.jakubpiecuch.trainingmanager.domain.Role;
import pl.jakubpiecuch.trainingmanager.service.repository.account.AccountCriteria;
import pl.jakubpiecuch.trainingmanager.service.repository.role.RoleCriteria;
import pl.jakubpiecuch.trainingmanager.service.user.model.Provider;
import pl.jakubpiecuch.trainingmanager.service.user.social.SocialProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class AccountDaoImplTest extends BaseUnitDaoTestCase {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
    private static final Long ID = 1l;
    private static final String NAME = "test.user";
    private static final String PASSWORD = "dcc4cec778c00b632fba26da142d95d0b46a05e0a5f944a0484346c0656def67";
    private static final String SALT = "jp88";
    private static final String CREATED_STRING = "2014-12-07 13:52:56.805";
    private static final Long NOT_EXISTS_ID = 99l;
    private static final String UPDATED_STRING = "2014-12-07 13:53:16.062";
    private static final Account.Status STATUS = Account.Status.ACTIVE;
    private static final String EMAIL = "test.user@test.com";
    private static final String FIRST_NAME = "Test";
    private static final String LAST_NAME = "User";
    private static final String CONFIG = "{\"firstName\":\"" + FIRST_NAME + "\",\"lastName\":\"" + LAST_NAME + "\"}";
    private static Date CREATED;
    private static Date UPDATED;

    static {
        try {
            CREATED = FORMAT.parse(CREATED_STRING);
            UPDATED = FORMAT.parse(UPDATED_STRING);
        } catch (ParseException e) {
        }
    }

    @Autowired
    private RepoDao<Account, AccountCriteria> accountDao;

    @Autowired
    private RepoDao<Role, RoleCriteria> roleDao;

    private static void assertAccount(Account account, Role role) {
        assertEquals(ID, account.getId());
        assertEquals(PASSWORD, account.getCredential());
        assertEquals(NAME, account.getName());
        assertEquals(SALT, account.getSalt());
        assertEquals(UPDATED, account.getUpdated());
        assertEquals(CREATED, account.getCreated());
        assertEquals(STATUS, account.getStatus());
        assertEquals(EMAIL, account.getEmail());
        assertEquals(CONFIG, account.getConfig());
        assertEquals(role, account.getRoles().get(0));
        assertEquals(Arrays.asList(Permissions.getAllPermissions()).stream().map(s -> Permissions.ROLE_PREFIX + s).sorted().collect(Collectors.toList()), account.getGrantedPermissions().stream().sorted().collect(Collectors.toList()));
    }

    @Test
    @Transactional
    public void testDelete() {
        Account account = new Account();
        account.setName(NAME + "delete");
        account.setSalt(SALT);
        account.setCredential(PASSWORD);
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
        account.setCredential(PASSWORD);
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
        account.setCredential(PASSWORD);
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
        } catch (HibernateException ex) {
            exFlag = true;
        }
        assertTrue(exFlag);
    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void testSaveNull() {
        accountDao.create(null);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testSaveValidityNoName() {
        Account account = new Account();
        account.setEmail(EMAIL);
        accountDao.create(account);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testSaveValidityNoPassword() {
        Account account = new Account();
        account.setEmail(EMAIL);
        account.setName(NAME);
        accountDao.create(account);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testSaveValidityNoSalt() {
        Account account = new Account();
        account.setEmail(EMAIL);
        account.setName(NAME);
        account.setCredential(PASSWORD);
        accountDao.create(account);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testSaveValidityNoStatus() {
        Account account = new Account();
        account.setEmail(EMAIL);
        account.setName(NAME);
        account.setCredential(PASSWORD);
        account.setSalt(SALT);
        accountDao.create(account);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testSaveValidityNoProvider() {
        Account account = new Account();
        account.setEmail(EMAIL);
        account.setName(NAME);
        account.setCredential(PASSWORD);
        account.setSalt(SALT);
        account.setStatus(STATUS);
        accountDao.create(account);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testSaveValidityNoSocialType() {
        Account account = new Account();
        account.setEmail(EMAIL);
        account.setName(NAME);
        account.setCredential(PASSWORD);
        account.setSalt(SALT);
        account.setStatus(STATUS);
        account.setProvider(Provider.Type.LOCAL);
        accountDao.create(account);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testSaveValidityNoUniqueNameInSocial() {
        Account account = new Account();
        account.setEmail(EMAIL);
        account.setName(NAME);
        account.setCredential(PASSWORD);
        account.setSalt(SALT);
        account.setStatus(STATUS);
        account.setProvider(Provider.Type.LOCAL);
        account.setSocialType(SocialProvider.SocialType.NONE);
        accountDao.create(account);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testSaveValidityNoUniqueEmailInSocial() {
        Account account = new Account();
        account.setEmail(EMAIL);
        account.setName(NAME + "uniqueName");
        account.setCredential(PASSWORD);
        account.setSalt(SALT);
        account.setStatus(STATUS);
        account.setProvider(Provider.Type.LOCAL);
        account.setSocialType(SocialProvider.SocialType.NONE);
        accountDao.create(account);
    }

    @Test
    public void testSaveValiditySuccess() {
        Account account = new Account();
        account.setEmail(EMAIL);
        account.setName(NAME);
        account.setCredential(PASSWORD);
        account.setSalt(SALT);
        account.setStatus(STATUS);
        account.setProvider(Provider.Type.LOCAL);
        account.setSocialType(SocialProvider.SocialType.FACEBOOK);
        accountDao.create(account);

        assertNotNull(account.getId());
        assertNotNull(account.getCreated());
    }

    @Test
    public void testFindByCriteria() {
        Role role = roleDao.findById(1l);
        PageResult<Account> result = accountDao.findByCriteria(null);

        assertEquals(0l, result.getCount());

        result = accountDao.findByCriteria(new AccountCriteria());

        assertEquals(1l, result.getCount());
        assertAccount(result.getResult().get(0), role);

        result = accountDao.findByCriteria(new AccountCriteria().addEmailRestrictions("wrong@email.com"));

        assertEquals(0l, result.getCount());

        result = accountDao.findByCriteria(new AccountCriteria().addEmailRestrictions(EMAIL));

        assertEquals(1l, result.getCount());
        assertAccount(result.getResult().get(0), role);

        result = accountDao.findByCriteria(new AccountCriteria().addEmailRestrictions(EMAIL).addNameRestrictions("wrong.name"));

        assertEquals(0l, result.getCount());

        result = accountDao.findByCriteria(new AccountCriteria().addEmailRestrictions(EMAIL).addNameRestrictions(NAME));

        assertEquals(1l, result.getCount());
        assertAccount(result.getResult().get(0), role);
    }
}