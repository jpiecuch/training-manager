package pl.jakubpiecuch.gymhome.service.resolver.order.description;

import pl.jakubpiecuch.gymhome.domain.Description;
import pl.jakubpiecuch.gymhome.service.resolver.AbstractOrderResolver;

/**
 * Created by Rico on 2015-01-19.
 */
public class MusclesOrderResolver extends AbstractOrderResolver<Description.Muscles> {

    @Override
    protected Description.Muscles[] values() {
        return Description.Muscles.values();
    }
}
