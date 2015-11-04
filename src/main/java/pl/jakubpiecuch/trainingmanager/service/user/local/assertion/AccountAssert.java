package pl.jakubpiecuch.trainingmanager.service.user.local.assertion;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.jakubpiecuch.trainingmanager.web.exception.notfound.NotFoundException;

/**
 * Created by jakub on 12.09.2015.
 */
public class AccountAssert {

    private static final String WRONG_USER = "wrong.user";

    public static void isTrue(boolean expression) {
        if (!expression) {
            throw new NotFoundException(WRONG_USER);
        }
    }
}
