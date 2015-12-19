package pl.jakubpiecuch.trainingmanager.service.user.model;

import pl.jakubpiecuch.trainingmanager.domain.Account;

/**
 * Created by Rico on 2014-12-06.
 */
public class Registration extends Authentication {
    public static final String NAME = "registration";
    public static final String ACCEPTED_FIELD = "accepted";

    boolean accepted;

    public Registration() {
        super();
    }

    public Registration(Account account) {
        super(account);
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
