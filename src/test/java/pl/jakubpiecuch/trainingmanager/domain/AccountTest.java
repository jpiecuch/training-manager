package pl.jakubpiecuch.trainingmanager.domain;

import org.junit.Before;
import org.junit.Test;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AccountTest extends AbstractEntityTest<Account> {

    public static final String CONFIG = "{\"firstName\":\"john\"}";
    private static final String EMAIL = "email";
    private static final Long ID = 1l;
    private static final Account.Status STATUS = Account.Status.ACTIVE;
    private static final Date CREATED = new Date();
    private static final String NAME = "name";
    private static final String PASSWORD = "pass";
    private static final String SALT = "salt";
    private static final Date UPDATED = new Date();
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";

    private Account account = new Account();

    @Before
    public void setUp() {
        account = new Account(ID);
        account.setId(ID);
        account.setEmail(EMAIL);
        account.setConfig(CONFIG);
        account.setStatus(STATUS);
        account.setCreated(CREATED);
        account.setName(NAME);
        account.setPassword(PASSWORD);
        account.setSalt(SALT);
        account.setUpdated(UPDATED);

    }

    @Test
    public void testAccount() {
        testEntity();
    }

    @Test
    public void testConfig() {
        Account.Config config = new Account.Config();
        assertNull(config.getFirstName());
        assertNull(config.getLastName());

        Account.Config.Builder builder = new Account.Config.Builder();
        config = builder.firstName(FIRST_NAME).lastName(LAST_NAME).build();
        assertEquals(FIRST_NAME, config.getFirstName());
        assertEquals(LAST_NAME, config.getLastName());

        assertEquals(WebUtil.toJson(config), config.toString());
    }

    @Override
    protected void assertEntity() {
        assertEquals(EMAIL, account.getEmail());
        assertEquals(ID, account.getId());
        assertEquals(CONFIG, account.getConfig());
        assertEquals(STATUS, account.getStatus());
        assertEquals(CREATED, account.getCreated());
        assertEquals(NAME, account.getName());
        assertEquals(PASSWORD, account.getPassword());
        assertEquals(SALT, account.getSalt());
        assertEquals(UPDATED, account.getUpdated());
    }

    @Override
    protected Account getEntity() {
        return account;
    }
}