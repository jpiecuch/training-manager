package pl.jakubpiecuch.trainingmanager.web.controllers.api;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.impl.Criteria;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;

import java.util.Locale;

/**
 * Created by jakub on 30.12.2015.
 */
@RestController
public interface RepositoryController<T> {

    PageResult<T> page(ApiVersionService.Version version, MultiValueMap<String,String> parameters,
                       Integer firstResult, Integer maxResults, Long[] excludedId,
                       String orderBy, Criteria.OrderMode orderMode,
                       Locale locale);
    T unique(ApiVersionService.Version version, long id);
    long create(ApiVersionService.Version version, T body);
    void update(ApiVersionService.Version version, long id, T body);
    void delete(ApiVersionService.Version version, long id);

}
