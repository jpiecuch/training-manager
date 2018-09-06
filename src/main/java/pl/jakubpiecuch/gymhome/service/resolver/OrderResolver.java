package pl.jakubpiecuch.gymhome.service.resolver;

import pl.jakubpiecuch.gymhome.dao.impl.Criteria;

/**
 * Created by Rico on 2015-01-19.
 */
public interface OrderResolver {
    String resolve(String lang, String alias, String property, Criteria.OrderMode mode);
}
