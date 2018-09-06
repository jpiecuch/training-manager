package pl.jakubpiecuch.gymhome.impl;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pl.jakubpiecuch.gymhome.BaseUnitDaoTestCase;
import pl.jakubpiecuch.gymhome.dao.PageResult;
import pl.jakubpiecuch.gymhome.dao.RepoDao;
import pl.jakubpiecuch.gymhome.dao.core.CoreDao;
import pl.jakubpiecuch.gymhome.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.gymhome.domain.Account;
import pl.jakubpiecuch.gymhome.domain.CommonEntity;
import pl.jakubpiecuch.gymhome.domain.Permissions;
import pl.jakubpiecuch.gymhome.domain.Role;
import pl.jakubpiecuch.gymhome.service.repository.account.AccountCriteria;
import pl.jakubpiecuch.gymhome.service.repository.role.RoleCriteria;
import pl.jakubpiecuch.gymhome.service.user.model.Provider;
import pl.jakubpiecuch.gymhome.service.user.social.SocialProvider;

import javax.validation.ConstraintViolationException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class AccountDaoImplTest extends BaseUnitDaoTestCase<Account> {

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
    public void shouldDelete() {
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
    public void shouldUpdate() {
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
    public void shouldSave() {
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
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionOfNullObject() {
        try {
            accountDao.create(null);
            fail();
        } catch(IllegalArgumentException ex) {
            assertEquals(CoreDaoImpl.NOT_NULL_OBJECT_REQUIRED, ex.getMessage());
        }
    }

    @Test
    public void shouldThrowConstraintViolationExceptionNoUniqueNameInSocial() {
        Account account = new Account();
        account.setEmail(EMAIL + "unique");
        account.setName(NAME);
        account.setCredential(PASSWORD);
        account.setSalt(SALT);
        account.setStatus(STATUS);
        account.setProvider(Provider.Type.LOCAL);
        account.setSocialType(SocialProvider.SocialType.NONE);
        try {
            accountDao.create(account);
            fail();
        } catch (org.hibernate.exception.ConstraintViolationException ex) {
            assertEquals("ACCOUNT_NAME_UNIQUE", ex.getConstraintName());
        }
    }

    @Test
    public void testSaveValidityNoUniqueEmailInSocial() {
        Account account = new Account();
        account.setEmail(EMAIL);
        account.setName(NAME + "uniqueName");
        account.setCredential(PASSWORD);
        account.setSalt(SALT);
        account.setStatus(STATUS);
        account.setProvider(Provider.Type.LOCAL);
        account.setSocialType(SocialProvider.SocialType.NONE);
        try {
            accountDao.create(account);
            fail();
        } catch (org.hibernate.exception.ConstraintViolationException ex) {
            assertEquals("ACCOUNT_EMAIL_UNIQUE", ex.getConstraintName());
        }
    }

    @Test
    public void testFindByCriteria() {
        Role role = roleDao.findById(1l);
        PageResult<Account> result = accountDao.findByCriteria(null);

        assertEquals(0l, result.getCount());

        result = accountDao.findByCriteria(new AccountCriteria());

        assertEquals(2l, result.getCount());
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

    @Override
    protected Account getEntity() {
        return new Account();
    }

    @Override
    protected CoreDao<Account> getDao() {
        return accountDao;
    }

    @Override
    protected List<String> getNotNullProperties() {
        return Lists.newArrayList("name", "provider", "credential", "socialType", "salt", "status");
    }
}