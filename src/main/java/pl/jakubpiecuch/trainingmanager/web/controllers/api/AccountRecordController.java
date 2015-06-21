package pl.jakubpiecuch.trainingmanager.web.controllers.api;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.AccountRecord;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.domain.Permissions;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;
import pl.jakubpiecuch.trainingmanager.service.repository.Repositories;
import pl.jakubpiecuch.trainingmanager.service.repository.accountrecord.AccountRecordCriteria;
import pl.jakubpiecuch.trainingmanager.service.repository.description.DescriptionCriteria;

import java.util.Date;
import java.util.Locale;

/**
 * Created by Rico on 2015-01-01.
 */
@RequestMapping(ApiURI.API_ACCOUNT_RECORD_PATH)
@RestController
public class AccountRecordController extends AbstractController {

    @PreAuthorize(value = Permissions.HAS_ROLE_PREFIX + Permissions.ACCOUNT_RECORD_VIEWER + Permissions.HAS_ROLE_SUFFIX)
    @RequestMapping(method = { RequestMethod.GET })
    public PageResult<Description> records(@PathVariable ApiVersionService.Version version,
                               @RequestParam(value = "type", required = false) AccountRecord.Type[] types,
                               @RequestParam(value = "from", required = false) Date from,
                               @RequestParam(value = "to", required = false) Date to,
                               @RequestParam(value = "firstResult", required = false, defaultValue = "0") Integer firstResult,
                               @RequestParam(value = "maxResults", required = false, defaultValue = "10") Integer maxResults,
                                           @RequestParam(value = "orderby", required = false, defaultValue = "date") String orderBy,
                                           @RequestParam(value = "ordermode", required = false, defaultValue = "ASC") Criteria.OrderMode orderMode,
                               Locale locale) {
        return versionServices.get(version).retrieveFromRepository(new AccountRecordCriteria(locale.getLanguage())
                .addDateRangeRestriction(from, to).addTypeRestrictions(types)
                .setMaxResultsRestriction(maxResults).setFirstResultRestriction(firstResult).setOrderBy(orderBy, orderMode, null), Repositories.ACCOUNT_RECORD);
    }

    @PreAuthorize(value = Permissions.HAS_ROLE_PREFIX + Permissions.ACCOUNT_RECORD_VIEWER + Permissions.HAS_ROLE_SUFFIX)
    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = { RequestMethod.GET })
    public Description record(@PathVariable ApiVersionService.Version version, @PathVariable long id, Locale locale) {
        return (Description) versionServices.get(version).retrieveFromRepository(new DescriptionCriteria(locale.getLanguage()).setIdRestriction(id), Repositories.DESCRIPTION).getResult().get(0);
    }

    @PreAuthorize(value = Permissions.HAS_ROLE_PREFIX + Permissions.ACCOUNT_RECORD_CREATOR + Permissions.HAS_ROLE_SUFFIX)
    @RequestMapping(method = { RequestMethod.POST })
    public long create(@PathVariable ApiVersionService.Version version, @RequestBody Description description) {
        return versionServices.get(version).storeInRepository(description, Repositories.ACCOUNT_RECORD);
    }

    @PreAuthorize(value = Permissions.HAS_ROLE_PREFIX + Permissions.ACCOUNT_RECORD_UPDATER + Permissions.HAS_ROLE_SUFFIX)
    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = { RequestMethod.PUT })
    public void update(@PathVariable ApiVersionService.Version version, @PathVariable long id, @RequestBody Description description) {
        description.setId(id);
        versionServices.get(version).updateInRepository(description, Repositories.ACCOUNT_RECORD);
    }

    @PreAuthorize(value = Permissions.HAS_ROLE_PREFIX + Permissions.ACCOUNT_RECORD_DELETER + Permissions.HAS_ROLE_SUFFIX)
    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = { RequestMethod.DELETE })
    public void delete(@PathVariable ApiVersionService.Version version, @PathVariable long id) {
        versionServices.get(version).removeFromRepository(id, Repositories.ACCOUNT_RECORD);
    }
}
