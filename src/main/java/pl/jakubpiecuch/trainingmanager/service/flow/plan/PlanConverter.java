package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.flow.AbstractFlowConverter;

/**
 * Created by Rico on 2014-12-31.
 */
public class PlanConverter extends AbstractFlowConverter<PlanDto, Plan> {

    @Override
    protected PlanDto convertTo(Plan entity) {

        PlanDto flow = new PlanDto();

        flow.setId(entity.getId());
        flow.setName(entity.getName());
        flow.setGoal(entity.getGoal());
        flow.setCreatorId(entity.getCreator().getId());

        return flow;
    }

    @Override
    protected Plan convertFrom(PlanDto flowObject) {
        Plan entity = new Plan();

        entity.setId(flowObject.getId());
        entity.setName(flowObject.getName());
        entity.setGoal(flowObject.getGoal());
        entity.setCreator(new Account(flowObject.getCreatorId()));
        return entity;
    }
}
