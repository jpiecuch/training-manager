package pl.jakubpiecuch.trainingmanager.web.controllers.api.flow.plan.phase;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jakubpiecuch.trainingmanager.service.flow.Flow;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.flow.AbstractFlowController;

/**
 * Created by Rico on 2014-12-31.
 */
@RequestMapping(ApiURI.API_PHASE_PATH)
@RestController
public class PhaseController extends AbstractFlowController {

    @Override
    protected Flow.Hierarchy getHierarchy() {
        return Flow.Hierarchy.PHASE;
    }
}
