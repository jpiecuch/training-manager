package pl.jakubpiecuch.trainingmanager.web.controllers.api.description;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.repository.description.DescriptionCriteria;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

import java.util.List;

/**
 * Created by Rico on 2015-01-01.
 */
@RequestMapping(ApiURI.API_DESCRIPTION_PATH)
@RestController
public class DescriptionController extends AbstractController {

    @RequestMapping(method = { RequestMethod.GET })
    public PageResult<Description> descriptions(@PathVariable ApiVersionService.Version version,
                               @RequestParam(value = "muscle", required = false) Description.PartyMuscles[] muscles,
                               @RequestParam(value = "force", required = false) Description.Force[] forces,
                               @RequestParam(value = "level", required = false) Description.Level[] levels,
                               @RequestParam(value = "type", required = false) Description.Type[] types,
                               @RequestParam(value = "mechanic", required = false) Description.Mechanics[] mechanics,
                               @RequestParam(value = "excludeId", required = false) Long[] excludedIds,
                               @RequestParam(value = "firstResult") Integer firstResult,
                               @RequestParam(value = "maxResults") Integer maxResults) throws Exception {
        return versionServices.get(version).descriptions(new DescriptionCriteria().addForceRestriction(forces).addLevelRestriction(levels)
                .addMechanicsRestriction(mechanics).addMuscleRestriction(muscles).addTypeRestriction(types).setFirstResultRestriction(firstResult)
                .setMaxResultsRestriction(maxResults).addExcludedIdRestriction(excludedIds));
    }
}
