package pl.jakubpiecuch.gymhome.service.user.model;

/**
 * Created by Rico on 2014-11-23.
 */
public interface Provider {
    Type getType();

    enum Type {
        LOCAL, SOCIAL
    }
}
