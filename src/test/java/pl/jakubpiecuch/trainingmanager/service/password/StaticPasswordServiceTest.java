package pl.jakubpiecuch.trainingmanager.service.password;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import pl.jakubpiecuch.trainingmanager.BaseIntegrationTestCase;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

/**
 * Created by jakub on 19.09.2015.
 */
public class StaticPasswordServiceTest extends BaseIntegrationTestCase {

    @Autowired
    private StaticPasswordService passwordService;

    @Resource(name="passwords")
    private List<String> passwords = new ArrayList<String>();
    
    @Value(value = "${user.password.pattern}")
    private String passwordPattern;
    
    @Value(value = "${user.password.maxLength}")
    private Integer maxPasswordLength;
    
    @Value(value = "${user.password.minLength}")
    private Integer minPasswordLength;

    @Test
    public void testGenerate() throws Exception {
        while (!passwords.isEmpty()) {
            String password = passwordService.generate();
            Pattern p = Pattern.compile(passwordPattern);
            Matcher m = p.matcher(password);
            assertTrue(m.matches());
            assertTrue(password.length() <= maxPasswordLength);
            assertTrue(password.length() >= minPasswordLength);
            passwords.remove(password);
            passwordService.setPasswords(passwords);
        }
    }
}