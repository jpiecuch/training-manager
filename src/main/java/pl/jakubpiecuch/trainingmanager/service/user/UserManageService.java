package pl.jakubpiecuch.trainingmanager.service.user;

import java.util.Locale;

/**
 * Created by Rico on 2014-11-22.
 */
public interface UserManageService {
    void password(String email, Locale locale);
    void activate(String id);
}
