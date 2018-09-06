package pl.jakubpiecuch.gymhome.service.mail;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class VelocityEmailService implements EmailService {

    private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;
    private String sender;
    private MessageSource messageSource;
    private String templateLocation;
    private String encoding;

    @Override
    @Async
    public void sendEmail(final Object[] data, final Locale locale, final Template template, final String... recipients) {
        Assert.notNull(template);
        Assert.notNull(locale);
        MimeMessagePreparator preparator = mimeMessage -> {

            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, encoding);
            message.setTo(recipients);
            message.setFrom(sender);
            message.setSubject(messageSource.getMessage("mail." + template + ".subject", null, locale));

            Map<String, Object> model = new HashMap<>();
            model.put("messageSource", messageSource);
            model.put("locale", locale);
            model.put("data", data);
            model.put("date", new DateTool());


            message.setText(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, String.format(templateLocation, template.name().toLowerCase()), encoding, model), true);

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
