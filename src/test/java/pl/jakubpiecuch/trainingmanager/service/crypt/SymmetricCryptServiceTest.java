package pl.jakubpiecuch.trainingmanager.service.crypt;

import com.springcryptoutils.core.cipher.Mode;
import com.springcryptoutils.core.cipher.symmetric.Base64EncodedCiphererImpl;
import com.springcryptoutils.core.cipher.symmetric.SymmetricEncryptionException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SymmetricCryptServiceTest {

    private final static String RAW_TEXT = "raw text";
    private final static String[] RAW_TEXT_ARRAY = new String[]{"raw", "text", "array"};
    private final static String ENC_TEXT = "qYevnGLdSpSNzAqPX8ARmQ==";
    private final static String ENC_TEXT_FROM_ARRAY = "rEunkz0zsA+4SH0XmIRmaA==";
    private SymmetricCryptService symmetricCryptService = new SymmetricCryptService();
    private Base64EncodedCiphererImpl decryptService = new Base64EncodedCiphererImpl();
    private Base64EncodedCiphererImpl encryptService = new Base64EncodedCiphererImpl();

    @Before
    public void setUp() {
        decryptService.setMode(Mode.DECRYPT);
        encryptService.setMode(Mode.ENCRYPT);
        symmetricCryptService.setDecryptService(decryptService);
        symmetricCryptService.setEncryptService(encryptService);
    }

    @Test
    public void testEncrypt() throws Exception {
        Assert.assertEquals(ENC_TEXT, symmetricCryptService.encrypt(RAW_TEXT));
    }

    @Test
    public void testDecrypt() throws Exception {
        Assert.assertEquals(RAW_TEXT, symmetricCryptService.decrypt(ENC_TEXT, null));
    }

    @Test
    public void testEncryptArray() throws Exception {
        Assert.assertEquals(ENC_TEXT_FROM_ARRAY, symmetricCryptService.encrypt(RAW_TEXT_ARRAY));
    }

    @Test
    public void testDecryptArray() throws Exception {
        Assert.assertEquals(RAW_TEXT_ARRAY[0], symmetricCryptService.decrypt(ENC_TEXT_FROM_ARRAY, 0));
        Assert.assertEquals(RAW_TEXT_ARRAY[1], symmetricCryptService.decrypt(ENC_TEXT_FROM_ARRAY, 1));
        Assert.assertEquals(RAW_TEXT_ARRAY[2], symmetricCryptService.decrypt(ENC_TEXT_FROM_ARRAY, 2));
    }

    @Test(expected = NullPointerException.class)
    public void testEncryptNull() throws Exception {
        symmetricCryptService.encrypt(null);
    }

    @Test(expected = SymmetricEncryptionException.class)
    public void testDecryptNullEncText() throws Exception {
        symmetricCryptService.decrypt(null, null);
    }
}