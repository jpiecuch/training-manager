package pl.jakubpiecuch.trainingmanager.web.controllers.api.flow.plan.phase.workout;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.flow.Flow;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.WorkoutDto;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.flow.AbstractFlowController;

/**
 * Created by Rico on 2014-12-31.
 */
@RequestMapping(ApiURI.API_WORKOUT_PATH)
@RestController
public class WorkoutController extends AbstractFlowController {

    @Override
    protected Flow.Hierarchy getHierarchy() {
        return Flow.Hierarchy.WORKOUT;
    }

    @RequestMapping(method = { RequestMethod.POST })
    public ResponseEntity create(@PathVariable ApiVersionService.Version version, @RequestBody WorkoutDto flow) throws Exception {
        return super.create(version, flow);
    }
}
