package pl.jakubpiecuch.gymhome.service.mail;

import java.util.Locale;

public interface EmailService {

    void sendEmail(Object[] data, Locale locale, Template template, String... recipients);

    enum Template {
        NEW_PASSWORD, REGISTER
    }
}
