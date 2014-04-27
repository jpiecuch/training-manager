package pl.jakubpiecuch.trainingmanager.service.mail;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.velocity.VelocityEngineUtils;

public class GmailService implements EmailService {

    private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;
    private String sender;
    private MessageSource messageSource;
    private String templateLocation;
    private String encoding;

    @Override
    @Async
    public void sendEmail(final Object data, final Locale locale, final Template template, final String... recipients) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {

                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, encoding);
                message.setTo(recipients);
                message.setFrom(sender);
                message.setSubject(messageSource.getMessage("mail." + template + ".subject", null, locale));

                Map<String, Object> model = new HashMap<String, Object>();
                model.put("messageSource", messageSource);
                model.put("locale", locale);
                model.put("data", data);
                model.put("date", new DateTool());
                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, String.format(templateLocation, template.toString().toLowerCase()), encoding, model);
                message.setText(text, true);

            }
        };

        this.mailSender.send(preparator);
    }

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Autowired
    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public void setTemplateLocation(String templateLocation) {
        this.templateLocation = templateLocation;
    }
}
