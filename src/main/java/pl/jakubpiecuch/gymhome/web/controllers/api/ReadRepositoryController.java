package pl.jakubpiecuch.gymhome.web.controllers.api;

import org.springframework.util.MultiValueMap;
import pl.jakubpiecuch.gymhome.dao.CountResult;
import pl.jakubpiecuch.gymhome.dao.PageResult;
import pl.jakubpiecuch.gymhome.dao.impl.Criteria;
import pl.jakubpiecuch.gymhome.service.api.ApiVersionService;

import java.util.Locale;
import java.util.Map;

/**
 * Created by Rico on 1/29/2017.
 */
public interface ReadRepositoryController<T> {

    PageResult<T> page(ApiVersionService.Version version, MultiValueMap<String, String> parameters,
                       Integer firstResult, Integer maxResults, Long[] excludedId,
                       String orderBy, Criteria.OrderMode orderMode,
                       Locale locale);
    CountResult count(ApiVersionService.Version version, MultiValueMap<String, String> parameters,
                      Long[] excludedId, Locale locale);
    Map<String, Long> group(ApiVersionService.Version version, MultiValueMap<String, String> parameters,
                            Long[] excludedId, String groupBy, Locale locale);
    T unique(ApiVersionService.Version version, long id);
}
