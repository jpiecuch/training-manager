package pl.jakubpiecuch.trainingmanager.service.user.model;

/**
 * Created by Rico on 2014-11-23.
 */
public interface Provider {
    Type getType();

    enum Type {
        LOCAL, SOCIAL
    }
}
