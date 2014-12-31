package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.flow.AbstractFlowObjectConverter;

/**
 * Created by Rico on 2014-12-31.
 */
public class PlanFlowConverter extends AbstractFlowObjectConverter<PlanFlow, Plan> {

    @Override
    protected PlanFlow convertTo(Plan entity) {

        PlanFlow flow = new PlanFlow();

        flow.setId(entity.getId());
        flow.setName(entity.getName());
        flow.setGoal(entity.getGoal());

        return flow;
    }

    @Override
    protected Plan convertFrom(PlanFlow flowObject) {
        Plan entity = new Plan();

        entity.setId(flowObject.getId());
        entity.setName(flowObject.getName());
        entity.setGoal(flowObject.getGoal());

        return entity;
    }
}
