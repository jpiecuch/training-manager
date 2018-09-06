package pl.jakubpiecuch.gymhome.web.controllers.api.plan;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.jakubpiecuch.gymhome.service.flow.plan.PlanCriteria;
import pl.jakubpiecuch.gymhome.service.flow.plan.PlanDto;
import pl.jakubpiecuch.gymhome.web.controllers.api.AbstractRepositoryController;
import pl.jakubpiecuch.gymhome.web.controllers.api.ApiURI;

import java.util.Locale;

/**
 * Created by Rico on 2014-12-31.
 */
@RequestMapping(ApiURI.API_PLAN_PATH)
public class PlanController extends AbstractRepositoryController<PlanDto, PlanCriteria> {

    @Override
    protected PlanCriteria createCriteria(MultiValueMap<String, String> parameters, Locale locale) {
        return new PlanCriteria(locale.getLanguage());
    }
}
