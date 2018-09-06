package pl.jakubpiecuch.gymhome.service.resolver.order.description;

import pl.jakubpiecuch.gymhome.domain.Description;
import pl.jakubpiecuch.gymhome.service.resolver.AbstractOrderResolver;

/**
 * Created by Rico on 2015-01-19.
 */
public class TypeOrderResolver extends AbstractOrderResolver<Description.Type> {

    @Override
    protected Description.Type[] values() {
        return Description.Type.values();
    }
}
