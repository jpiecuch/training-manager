package pl.jakubpiecuch.gymhome.service.user.local;

import pl.jakubpiecuch.gymhome.service.user.model.Provider;

/**
 * Created by Rico on 2014-11-23.
 */
public class LocalProvider implements Provider {
    protected final String id;

    public LocalProvider(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public Type getType() {
        return Type.LOCAL;
    }
}
