package pl.jakubpiecuch.trainingmanager.service.user;

/**
 * Created by Rico on 2014-11-23.
 */
public interface Provider {
    enum Type {
        LOCAL, SOCIAL
    }

    Type getType();
}
