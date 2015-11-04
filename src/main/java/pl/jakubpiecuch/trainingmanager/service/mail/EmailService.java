package pl.jakubpiecuch.trainingmanager.service.mail;

import java.util.Locale;

public interface EmailService {
    
    enum Template {
        NEW_PASSWORD, REGISTER
    }
    
    void sendEmail(Object[] data, Locale locale, Template template, String... recipients);
}
