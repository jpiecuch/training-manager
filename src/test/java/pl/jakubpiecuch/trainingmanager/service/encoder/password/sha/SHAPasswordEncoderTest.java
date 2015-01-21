package pl.jakubpiecuch.trainingmanager.service.encoder.password.sha;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SHAPasswordEncoderTest {

    private final static int STRENGTH = 256;
    private final static String RAW_PASS = "raw-pass";
    private final static String SALT = "salt";
    private final static String ENCODED_PASS = "3330824b1a76888818421fa17e80725dae4638ff2e11e119d9186098ac2ebbb0";
    private SHAPasswordEncoder encoder = new SHAPasswordEncoder();

    @Before
    public void setUp() throws Exception{
        encoder.setStrength(STRENGTH);
        encoder.afterPropertiesSet();
    }

    @Test
    public void testEncode() throws Exception {
        Assert.assertEquals(ENCODED_PASS,encoder.encode(RAW_PASS, SALT));
    }

    @Test
    public void testIsValid() throws Exception {
        Assert.assertTrue(encoder.isValid(ENCODED_PASS, RAW_PASS, SALT));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncodeWithNotValidSalt() {
        encoder.encode(RAW_PASS, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEncodeWithNotValidPass() {
        encoder.encode(null, SALT);
    }
}