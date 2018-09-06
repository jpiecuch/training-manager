package pl.jakubpiecuch.gymhome.web.controllers.api.description;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jakubpiecuch.gymhome.domain.Description;
import pl.jakubpiecuch.gymhome.service.repository.description.DescriptionCriteria;
import pl.jakubpiecuch.gymhome.web.controllers.api.AbstractRepositoryController;
import pl.jakubpiecuch.gymhome.web.controllers.api.ApiURI;

import java.util.Locale;

/**
 * Created by Rico on 2015-01-01.
 */
@RequestMapping(ApiURI.API_DESCRIPTION_PATH)
@RestController
public class DescriptionController extends AbstractRepositoryController<Description, DescriptionCriteria> {

    @Override
    protected DescriptionCriteria createCriteria(MultiValueMap<String, String> parameters, Locale locale) {
        return new DescriptionCriteria(locale.getLanguage())
                .addNameLikeRestriction(parameters.getFirst("name"))
                .addForceRestriction(resolveEnumValues(parameters.get("force"), Description.Force.class))
                .addLevelRestriction(resolveEnumValues(parameters.get("level"), Description.Level.class))
                .addMechanicsRestriction(resolveEnumValues(parameters.get("mechanic"), Description.Mechanics.class))
                .addMuscleRestriction(resolveEnumValues(parameters.get("muscle"), Description.Muscles.class))
                .addTypeRestriction(resolveEnumValues(parameters.get("type"), Description.Type.class));
    }
}
