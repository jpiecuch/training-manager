package pl.jakubpiecuch.trainingmanager.web.controllers.api.description;

import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;
import pl.jakubpiecuch.trainingmanager.service.repository.Repositories;
import pl.jakubpiecuch.trainingmanager.service.repository.description.DescriptionCriteria;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

import java.util.Locale;

/**
 * Created by Rico on 2015-01-01.
 */
@RequestMapping(ApiURI.API_DESCRIPTION_PATH)
@RestController
public class DescriptionController extends AbstractController {

    @RequestMapping(method = { RequestMethod.GET })
    public PageResult<Description> descriptions(@PathVariable ApiVersionService.Version version,
                               @RequestParam(value = "muscle", required = false) Description.Muscles[] muscles,
                               @RequestParam(value = "force", required = false) Description.Force[] forces,
                               @RequestParam(value = "level", required = false) Description.Level[] levels,
                               @RequestParam(value = "type", required = false) Description.Type[] types,
                               @RequestParam(value = "mechanic", required = false) Description.Mechanics[] mechanics,
                               @RequestParam(value = "excludeId", required = false) Long[] excludedIds,
                               @RequestParam(value = "firstResult", required = false, defaultValue = "0") Integer firstResult,
                               @RequestParam(value = "maxResults", required = false, defaultValue = "10") Integer maxResults,
                               @RequestParam(value = "orderby", required = false, defaultValue = "id") String orderBy,
                               @RequestParam(value = "ordermode", required = false, defaultValue = "ASC") Criteria.OrderMode orderMode,
                               Locale locale) throws Exception {
        return versionServices.get(version).retrieveFromRepository(new DescriptionCriteria(locale.getLanguage()).addForceRestriction(forces).addLevelRestriction(levels)
                .addMechanicsRestriction(mechanics).addMuscleRestriction(muscles).addTypeRestriction(types).setFirstResultRestriction(firstResult)
                .setMaxResultsRestriction(maxResults).addExcludedIdRestriction(excludedIds).setOrderBy(orderBy,orderMode, versionServices.get(version).orderResolvers()), Repositories.DESCRIPTION);
    }

    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = { RequestMethod.GET })
    public Description description(@PathVariable ApiVersionService.Version version, @PathVariable long id, Locale locale) {
        return (Description) versionServices.get(version).retrieveFromRepository(new DescriptionCriteria(locale.getLanguage()).setIdRestriction(id), Repositories.DESCRIPTION).getResult().get(0);
    }

    @RequestMapping(method = { RequestMethod.POST })
    public long create(@PathVariable ApiVersionService.Version version, @RequestBody Description description) {
        return versionServices.get(version).storeInRepository(description, Repositories.DESCRIPTION);
    }

    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = { RequestMethod.PUT })
    public void update(@PathVariable ApiVersionService.Version version, @PathVariable long id, @RequestBody Description description) {
        versionServices.get(version).updateInRepository(description, Repositories.DESCRIPTION);
    }

    @RequestMapping(value = ApiURI.ID_PATH_PARAM, method = { RequestMethod.DELETE })
    public void delete(@PathVariable ApiVersionService.Version version, @PathVariable long id) {
        versionServices.get(version).removeFromRepository(id, Repositories.DESCRIPTION);
    }
}
