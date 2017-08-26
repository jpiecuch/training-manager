package pl.jakubpiecuch.gymhome.service.user;

import pl.jakubpiecuch.gymhome.service.user.model.Registration;

import java.util.Locale;

/**
 * Created by jakub on 31.12.2015.
 */
public interface SignOnService {
    void signOn(Registration request, Locale locale);
}
