package pl.jakubpiecuch.gymhome.web.controllers.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.jakubpiecuch.gymhome.dao.impl.Criteria;
import pl.jakubpiecuch.gymhome.service.api.ApiVersionService;
import pl.jakubpiecuch.gymhome.service.repository.*;

/**
 * Created by jakub on 30.12.2015.
 */
public abstract class AbstractRepositoryController<T extends RepoObject, C extends Criteria> extends AbstractReadRepositoryController<T, C> implements RepositoryController<T> {

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public long create(@PathVariable ApiVersionService.Version version, @RequestBody T body) {
        return storageRepository(version).create(body);
    }

    @Override
    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = RequestMethod.DELETE)
    public void delete(@PathVariable ApiVersionService.Version version, @PathVariable long id) {
        storageRepository(version).delete(id);
    }

    @Override
    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = RequestMethod.PUT)
    public void update(@PathVariable ApiVersionService.Version version, @PathVariable long id, @RequestBody T body) {
        updatableRepository(version).update(body);
    }

    private StorageRepository storageRepository(ApiVersionService.Version version) {
        return versionServices.get(version).store(repositoryType);
    }

    private UpdatableRepository updatableRepository(ApiVersionService.Version version) {
        return versionServices.get(version).update(repositoryType);
    }
}
