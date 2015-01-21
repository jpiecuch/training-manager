package pl.jakubpiecuch.trainingmanager.service.flow.plan.phase;

import pl.jakubpiecuch.trainingmanager.domain.Phase;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.flow.AbstractFlowConverter;

/**
 * Created by Rico on 2014-12-31.
 */
public class PhaseConverter extends AbstractFlowConverter<PhaseDto, Phase> {

    @Override
    protected PhaseDto convertTo(Phase entity, boolean full) {

        PhaseDto flow = new PhaseDto();

        flow.setId(entity.getId());
        flow.setDescription(entity.getDescription());
        flow.setGoal(entity.getGoal());
        flow.setPlanId(entity.getPlan().getId());
        flow.setPosition(entity.getPosition());
        flow.setWeeks(entity.getWeeks());
        flow.setWorkouts(full ? manager.children(entity.getId(), full) : null);
        return flow;
    }

    @Override
    protected Phase convertFrom(PhaseDto flowObject) {
        Phase entity = new Phase();

        entity.setId(flowObject.getId());
        entity.setDescription(flowObject.getDescription());
        entity.setGoal(flowObject.getGoal());
        entity.setPlan(new Plan(flowObject.getPlanId()));
        entity.setPosition(flowObject.getPosition());
        entity.setWeeks(flowObject.getWeeks());

        return entity;
    }
}
