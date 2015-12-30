package pl.jakubpiecuch.trainingmanager.web.controllers.api.role;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jakubpiecuch.trainingmanager.domain.Role;
import pl.jakubpiecuch.trainingmanager.service.repository.role.RoleCriteria;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.AbstractRepositoryController;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;

import java.util.Locale;

/**
 * Created by Rico on 2015-01-01.
 */
@RequestMapping(ApiURI.API_ROLE_PATH)
@RestController
public class RoleController extends AbstractRepositoryController<Role, RoleCriteria> {

    @Override
    protected RoleCriteria createCriteria(MultiValueMap<String, String> parameters, Locale locale) {
        return new RoleCriteria().addNameRestrictions(parameters.getFirst("name"));
    }
}
