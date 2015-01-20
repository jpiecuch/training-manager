package pl.jakubpiecuch.trainingmanager.service.resolver.order.description;

import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.service.resolver.AbstractOrderResolver;

/**
 * Created by Rico on 2015-01-20.
 */
public class ForceOrderResolver extends AbstractOrderResolver<Description.Force> {

    @Override
    protected Description.Force[] values() {
        return Description.Force.values();
    }

}