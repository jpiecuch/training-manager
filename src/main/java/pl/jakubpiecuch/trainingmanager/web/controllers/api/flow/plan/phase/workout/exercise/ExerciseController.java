package pl.jakubpiecuch.trainingmanager.web.controllers.api.flow.plan.phase.workout.exercise;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.jakubpiecuch.trainingmanager.service.api.ApiVersionService;
import pl.jakubpiecuch.trainingmanager.service.flow.Flow;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.exercise.ExerciseDto;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.ApiURI;
import pl.jakubpiecuch.trainingmanager.web.controllers.api.flow.AbstractFlowController;

/**
 * Created by Rico on 2014-12-31.
 */
@RequestMapping(ApiURI.API_EXERCISE_PATH)
@RestController
public class ExerciseController extends AbstractFlowController {

    @Override
    protected Flow.Hierarchy getHierarchy() {
        return Flow.Hierarchy.EXERCISE;
    }

    @RequestMapping(method = { RequestMethod.POST })
    public ResponseEntity create(@PathVariable ApiVersionService.Version version, @RequestBody ExerciseDto flow) throws Exception {
        return super.create(version, flow);
    }
}
