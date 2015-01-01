package pl.jakubpiecuch.trainingmanager.web.controllers.api.flow.plan.phase;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.flow.Flow;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.PhaseDto;
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

    @RequestMapping(method = { RequestMethod.POST })
    public ResponseEntity create(@PathVariable ApiVersionService.Version version, @RequestBody PhaseDto flow) throws Exception {
        return super.create(version, flow);
    }
}
