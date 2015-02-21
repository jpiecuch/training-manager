package pl.jakubpiecuch.trainingmanager.service.resolver;

import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;

/**
 * Created by Rico on 2015-01-19.
 */
public interface OrderResolver {
    String resolve(String lang, String alias, String property, Criteria.OrderMode mode);
}
