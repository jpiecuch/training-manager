package pl.jakubpiecuch.trainingmanager.web.controllers.api;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.impl.Criteria;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.repository.*;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jakub on 30.12.2015.
 */
public abstract class AbstractRepositoryController<T extends RepoObject, C extends Criteria> extends AbstractController implements RepositoryController<T> {

    protected abstract C createCriteria(MultiValueMap<String, String> parameters, Locale locale);
    private String defaultOrderBy;
    private RepositoryType repositoryType;

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public PageResult<T> page(@PathVariable ApiVersionService.Version version,
                              @RequestParam MultiValueMap<String, String> parameters,
                              @RequestParam(required = false, defaultValue = "0") Integer firstResult,
                              @RequestParam(required =  false, defaultValue = "10") Integer maxResults,
                              @RequestParam(required = false) Long[] excludedId,
                              @RequestParam(value = "orderby", required = false) String orderBy,
                              @RequestParam(value = "ordermode", required = false, defaultValue = "ASC") Criteria.OrderMode orderMode,
                              Locale locale) {
        return readRepository(version).page(createCriteria(parameters, locale)
                .setFirstResultRestriction(firstResult)
                .setMaxResultsRestriction(maxResults)
                .addExcludedIdRestriction(excludedId)
                .setOrderBy(ObjectUtils.firstNonNull(orderBy, defaultOrderBy), orderMode));
    }

    @Override
    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = RequestMethod.GET)
    public T unique(@PathVariable ApiVersionService.Version version, @PathVariable long id) {
        return (T) readRepository(version).unique(id);
    }

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

    protected <T extends Enum> T[] resolveEnumValues(List<String> values, Class<T> clazz) {
        if (CollectionUtils.isNotEmpty(values)) {
            Stream<T> tStream = values.stream().map(s -> T.valueOf(clazz, s));
            return tStream.collect(Collectors.toList()).toArray((T[]) Array.newInstance(clazz, values.size()));
        }
        return (T[]) Array.newInstance(clazz, 0);
    }

    protected Date resolveDate(String date) {
        try {
            return StringUtils.isNotEmpty(date) ? new SimpleDateFormat("yyyy-MM-dd").parse(date) : null;
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private ReadRepository readRepository(ApiVersionService.Version version) {
        return versionServices.get(version).read().get(repositoryType);
    }

    private StorageRepository storageRepository(ApiVersionService.Version version) {
        return versionServices.get(version).store(repositoryType);
    }

    private UpdatableRepository updatableRepository(ApiVersionService.Version version) {
        return versionServices.get(version).update(repositoryType);
    }

    public void setRepositoryType(RepositoryType repositoryType) {
        this.repositoryType = repositoryType;
    }

    public void setDefaultOrderBy(String defaultOrderBy) {
        this.defaultOrderBy = defaultOrderBy;
    }
}
