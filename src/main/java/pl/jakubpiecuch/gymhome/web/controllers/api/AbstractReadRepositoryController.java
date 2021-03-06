package pl.jakubpiecuch.gymhome.web.controllers.api;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.jakubpiecuch.gymhome.dao.CountResult;
import pl.jakubpiecuch.gymhome.dao.PageResult;
import pl.jakubpiecuch.gymhome.dao.impl.Criteria;
import pl.jakubpiecuch.gymhome.service.api.ApiVersionService;
import pl.jakubpiecuch.gymhome.service.repository.*;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by jakub on 30.12.2015.
 */
public abstract class AbstractReadRepositoryController<T extends RepoObject, C extends Criteria> extends AbstractController implements ReadRepositoryController<T> {

    private String defaultOrderBy;
    protected RepositoryType repositoryType;

    protected abstract C createCriteria(MultiValueMap<String, String> parameters, Locale locale);

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
    @RequestMapping(value = ApiURI.API_COUNT_PATH, method = RequestMethod.GET)
    public CountResult count(@PathVariable ApiVersionService.Version version,
                             @RequestParam MultiValueMap<String, String> parameters,
                             @RequestParam(required = false) Long[] excludedId,
                             Locale locale) {
        return readRepository(version).count(createCriteria(parameters, locale)
                .addExcludedIdRestriction(excludedId));
    }

    @Override
    @RequestMapping(value = ApiURI.API_GROUP_PATH, method = RequestMethod.GET)
    public Map<String, Long> group(@PathVariable ApiVersionService.Version version,
                                   @RequestParam MultiValueMap<String, String> parameters,
                                   @RequestParam(required = false) Long[] excludedId,
                                   @RequestParam String groupBy,
                                   Locale locale) {
        return readRepository(version).group(createCriteria(parameters, locale)
                .addExcludedIdRestriction(excludedId).addGroupBy(groupBy));
    }

    @Override
    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = RequestMethod.GET)
    public T unique(@PathVariable ApiVersionService.Version version, @PathVariable long id) {
        return (T) readRepository(version).unique(id);
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
        return versionServices.get(version).read(repositoryType);
    }

    public void setRepositoryType(RepositoryType repositoryType) {
        this.repositoryType = repositoryType;
    }

    public void setDefaultOrderBy(String defaultOrderBy) {
        this.defaultOrderBy = defaultOrderBy;
    }
}
