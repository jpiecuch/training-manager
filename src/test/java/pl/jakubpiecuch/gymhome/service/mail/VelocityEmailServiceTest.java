package pl.jakubpiecuch.gymhome.service.mail;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.internet.MimeMessage;
import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class VelocityEmailServiceTest {

    private static final Locale VALID_LOCALE = new Locale("en");
    private static final EmailService.Template VALID_TEMPLATE = EmailService.Template.REGISTER;
    //ResourceNotFoundException
    private static final String VALID_RECIPIENT = "test@test.com";
    private static final String VALID_SENDER = "send@test.com";
    private static final String VALID_SUBJECT = "subject";
    private static final String VALID_ENCODING = "UTF-8";
    private static final String TEMPLATE_LOCATION = "location";
    @InjectMocks
    private VelocityEmailService emailService;
    @Mock
    private MessageSource messageSource;
    @Mock
    private VelocityEngine velocityEngine;
    private JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    @Before
    public void setUp() {
        Mockito.when(messageSource.getMessage("mail." + VALID_TEMPLATE + ".subject", null, VALID_LOCALE)).thenReturn(VALID_SUBJECT);
        JavaMailSenderImpl spy = Mockito.spy(mailSender);
        Mockito.doNothing().when(spy).send(Mockito.any(MimeMessage[].class));
        emailService.setMailSender(spy);
        emailService.setSender(VALID_SENDER);
        emailService.setEncoding(VALID_ENCODING);
        emailService.setTemplateLocation(TEMPLATE_LOCATION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendEmailNullLocale() throws Exception {
        emailService.sendEmail(null, null, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSendEmailNullTemplate() throws Exception {
        emailService.sendEmail(null, VALID_LOCALE, null, null);
    }

    @Test
    public void testSendEmail() throws Exception {
        emailService.sendEmail(null, VALID_LOCALE, VALID_TEMPLATE, VALID_RECIPIENT);
    }
}