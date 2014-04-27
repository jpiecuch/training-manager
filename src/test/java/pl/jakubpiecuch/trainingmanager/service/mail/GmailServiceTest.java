package pl.jakubpiecuch.trainingmanager.service.mail;

import java.util.Locale;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.AbstractBaseTest;


public class GmailServiceTest extends AbstractBaseTest {
    
    @Autowired
    private EmailService emailService;
    
    @Test
    public void sendEmailTest() {
        String[] data = new String[] {"dshjdhsduhe"};
        emailService.sendEmail(data, Locale.ENGLISH, EmailService.Template.REGISTER, "jakub.piecuch@jakubpiecuch.pl");
    }
}
