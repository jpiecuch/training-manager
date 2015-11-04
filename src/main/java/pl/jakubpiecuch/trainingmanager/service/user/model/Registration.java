package pl.jakubpiecuch.trainingmanager.service.user.model;

import pl.jakubpiecuch.trainingmanager.domain.Account;

/**
 * Created by Rico on 2014-12-06.
 */
public class Registration extends Authentication {
    public static final String NAME = "registration";

    private String repeat;

    public Registration() {
        super();
    }

    public Registration(Account account) {
        super(account);
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }
}
