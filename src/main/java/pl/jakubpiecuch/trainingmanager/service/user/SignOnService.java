package pl.jakubpiecuch.trainingmanager.service.user;

import pl.jakubpiecuch.trainingmanager.service.user.model.Registration;

import java.util.Locale;

/**
 * Created by jakub on 31.12.2015.
 */
public interface SignOnService {
    void signOn(Registration request, Locale locale);
}
