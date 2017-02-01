package pl.jakubpiecuch.trainingmanager.web.controllers.api;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;
import pl.jakubpiecuch.trainingmanager.dao.CountResult;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.impl.Criteria;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;

import java.util.Locale;
import java.util.Map;

/**
 * Created by jakub on 30.12.2015.
 */
@RestController
public interface RepositoryController<T> extends ReadRepositoryController<T> {

    long create(ApiVersionService.Version version, T body);
    void update(ApiVersionService.Version version, long id, T body);
    void delete(ApiVersionService.Version version, long id);

}
