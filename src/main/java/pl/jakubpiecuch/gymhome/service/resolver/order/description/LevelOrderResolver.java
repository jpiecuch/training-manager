package pl.jakubpiecuch.gymhome.service.resolver.order.description;

import pl.jakubpiecuch.gymhome.domain.Description;
import pl.jakubpiecuch.gymhome.service.resolver.AbstractOrderResolver;

/**
 * Created by Rico on 2015-01-20.
 */
public class LevelOrderResolver extends AbstractOrderResolver<Description.Level> {

    @Override
    protected Description.Level[] values() {
        return Description.Level.values();
    }

}
