package pl.jakubpiecuch.gymhome.service.user.local.assertion;

import pl.jakubpiecuch.gymhome.web.exception.notfound.NotFoundException;

/**
 * Created by jakub on 12.09.2015.
 */
public class AccountAssert {

    private static final String WRONG_USER = "wrong.user";

    private AccountAssert() {
    }

    public static void isTrue(boolean expression) {
        if (!expression) {
            throw new NotFoundException(WRONG_USER);
        }
    }
}
