package pl.jakubpiecuch.trainingmanager.service;

import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;

/**
 * Created by Rico on 2015-01-19.
 */
public interface OrderResolver<T extends Enum> {
    String resolve(String lang, String alias, String property, Criteria.OrderMode mode);
}
