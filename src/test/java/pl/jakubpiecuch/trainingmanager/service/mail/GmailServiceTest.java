package pl.jakubpiecuch.trainingmanager.service.mail;

import java.util.Locale;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.jakubpiecuch.trainingmanager.AbstractBaseTest;
import pl.jakubpiecuch.trainingmanager.domain.Users;


public class GmailServiceTest extends AbstractBaseTest {
    
    @Autowired
    private EmailService emailService;
    
    @Test
    public void sendEmailTest() {
        Users user = new Users();
        user.setFirstName("Jan");
        Object[] data = new Object[] {"dshjdhsduhe", user};
        //emailService.sendEmail(data, Locale.ENGLISH, EmailService.Template.REGISTER, "jakub.piecuch@jakubpiecuch.pl");
    }
}
