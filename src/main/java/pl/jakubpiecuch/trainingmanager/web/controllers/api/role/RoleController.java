package pl.jakubpiecuch.trainingmanager.web.controllers.api.role;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Permissions;
import pl.jakubpiecuch.trainingmanager.domain.Role;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;
import pl.jakubpiecuch.trainingmanager.service.repository.Repositories;
import pl.jakubpiecuch.trainingmanager.service.repository.role.RoleCriteria;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

import java.util.Locale;

/**
 * Created by Rico on 2015-01-01.
 */
@RequestMapping(ApiURI.API_ROLE_PATH)
@RestController
public class RoleController extends AbstractController {

    @PreAuthorize(value = Permissions.HAS_ROLE_PREFIX + Permissions.ROLE_VIEWER + Permissions.HAS_ROLE_SUFFIX)
    @RequestMapping(method = {RequestMethod.GET})
    public PageResult<Role> roles(@PathVariable ApiVersionService.Version version,
                                  @RequestParam(value = "name", required = false) String[] names,
                                  @RequestParam(value = "excludeId", required = false) Long[] excludedIds,
                                  @RequestParam(value = "firstResult", required = false, defaultValue = "0") Integer firstResult,
                                  @RequestParam(value = "maxResults", required = false, defaultValue = "10") Integer maxResults,
                                  @RequestParam(value = "orderby", required = false, defaultValue = "id") String orderBy,
                                  @RequestParam(value = "ordermode", required = false, defaultValue = "ASC") Criteria.OrderMode orderMode,
                                  Locale locale) {
        return versionServices.get(version).retrieveFromRepository(new RoleCriteria().addNameRestrictions(names).setFirstResultRestriction(firstResult)
                .setMaxResultsRestriction(maxResults).addExcludedIdRestriction(excludedIds).setOrderBy(orderBy, orderMode, versionServices.get(version).orderResolvers()), Repositories.ROLE);
    }

    @PreAuthorize(value = Permissions.HAS_ROLE_PREFIX + Permissions.ROLE_VIEWER + Permissions.HAS_ROLE_SUFFIX)
    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = {RequestMethod.GET})
    public Role role(@PathVariable ApiVersionService.Version version, @PathVariable long id, Locale locale) {
        return (Role) versionServices.get(version).retrieveFromRepository(new RoleCriteria().setIdRestriction(id), Repositories.ROLE).getResult().get(0);
    }

    @PreAuthorize(value = Permissions.HAS_ROLE_PREFIX + Permissions.ROLE_CREATOR + Permissions.HAS_ROLE_SUFFIX)
    @RequestMapping(method = {RequestMethod.POST})
    public long create(@PathVariable ApiVersionService.Version version, @RequestBody Role description) {
        return versionServices.get(version).storeInRepository(description, Repositories.ROLE);
    }

    @PreAuthorize(value = Permissions.HAS_ROLE_PREFIX + Permissions.ROLE_UPDATER + Permissions.HAS_ROLE_SUFFIX)
    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = {RequestMethod.PUT})
    public void update(@PathVariable ApiVersionService.Version version, @PathVariable long id, @RequestBody Role description) {
        description.setId(id);
        versionServices.get(version).updateInRepository(description, Repositories.ROLE);
    }

    @PreAuthorize(value = Permissions.HAS_ROLE_PREFIX + Permissions.ROLE_DELETER + Permissions.HAS_ROLE_SUFFIX)
    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = {RequestMethod.DELETE})
    public void delete(@PathVariable ApiVersionService.Version version, @PathVariable long id) {
        versionServices.get(version).removeFromRepository(id, Repositories.ROLE);
    }
}
