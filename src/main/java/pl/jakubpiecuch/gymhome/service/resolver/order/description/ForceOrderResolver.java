package pl.jakubpiecuch.gymhome.service.resolver.order.description;

import pl.jakubpiecuch.gymhome.domain.Description;
import pl.jakubpiecuch.gymhome.service.resolver.AbstractOrderResolver;

/**
 * Created by Rico on 2015-01-20.
 */
public class ForceOrderResolver extends AbstractOrderResolver<Description.Force> {

    @Override
    protected Description.Force[] values() {
        return Description.Force.values();
    }

}