package pl.jakubpiecuch.gymhome.service.crypt;

import com.springcryptoutils.core.cipher.symmetric.Base64EncodedCipherer;
import org.apache.commons.lang3.StringUtils;

public class SymmetricCryptService implements CryptService {

    public static final String SLASH = "/";
    public static final String BACKSLASH = "\\";
    protected static final String VAL_SPLITTER = "|";
    private static final String IV = "AQIDBAUGAQI=";
    private static final String KEY = "Rs3xEA16I52XJpsWwkw4GrB8l6FiVGK/";
    private Base64EncodedCipherer encryptService;
    private Base64EncodedCipherer decryptService;

    @Override
    public String encrypt(String... args) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            builder.append(args[i]).append(i < args.length - 1 ? VAL_SPLITTER : StringUtils.EMPTY);
        }
        return encryptService.encrypt(KEY, IV, builder.toString()).replace(SLASH, BACKSLASH);
    }

    @Override
    public String decrypt(String arg, Integer position) {
        String result = decryptService.encrypt(KEY, IV, StringUtils.replace(arg, BACKSLASH, SLASH));
        return position != null ? StringUtils.split(result, VAL_SPLITTER)[position] : result;
    }

    public void setDecryptService(Base64EncodedCipherer decryptService) {
        this.decryptService = decryptService;
    }

    public void setEncryptService(Base64EncodedCipherer encryptService) {
        this.encryptService = encryptService;
    }
}
