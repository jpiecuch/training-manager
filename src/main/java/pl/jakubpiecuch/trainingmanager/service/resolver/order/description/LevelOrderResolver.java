package pl.jakubpiecuch.trainingmanager.service.resolver.order.description;

import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.service.resolver.AbstractOrderResolver;

/**
 * Created by Rico on 2015-01-20.
 */
public class LevelOrderResolver extends AbstractOrderResolver<Description.Level> {

    @Override
    protected Description.Level[] values() {
        return Description.Level.values();
    }

}
