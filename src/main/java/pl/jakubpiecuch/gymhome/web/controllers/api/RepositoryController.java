package pl.jakubpiecuch.gymhome.web.controllers.api;

import org.springframework.web.bind.annotation.RestController;
import pl.jakubpiecuch.gymhome.service.api.ApiVersionService;

/**
 * Created by jakub on 30.12.2015.
 */
@RestController
public interface RepositoryController<T> extends ReadRepositoryController<T> {

    long create(ApiVersionService.Version version, T body);
    void update(ApiVersionService.Version version, long id, T body);
    void delete(ApiVersionService.Version version, long id);

}
