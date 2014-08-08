package pl.jakubpiecuch.trainingmanager.service.crypt;

import com.springcryptoutils.core.cipher.symmetric.Base64EncodedCipherer;
import org.springframework.beans.factory.annotation.Autowired;

public class SymmetricCryptService implements CryptService {

    private Base64EncodedCipherer encryptService;
    private Base64EncodedCipherer decryptService;
    private static final String IV = "AQIDBAUGAQI=";
    private static final String KEY = "Rs3xEA16I52XJpsWwkw4GrB8l6FiVGK/";
    private static final String VAL_SPLITTER = "/";

    @Override
    public String encrypt(String... args) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            builder.append(args[i]).append(i < args.length - 1 ? VAL_SPLITTER : "");
        }
        return encryptService.encrypt(KEY, IV, builder.toString());
    }

    @Override
    public String decrypt(String arg, int position) {
        return decryptService.encrypt(KEY, IV, arg).split(VAL_SPLITTER)[position];
    }

    @Autowired
    public void setDecryptService(Base64EncodedCipherer decryptService) {
        this.decryptService = decryptService;
    }

    @Autowired
    public void setEncryptService(Base64EncodedCipherer encryptService) {
        this.encryptService = encryptService;
    }
}
