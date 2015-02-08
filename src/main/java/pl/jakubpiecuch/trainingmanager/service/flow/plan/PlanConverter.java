package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.flow.AbstractFlowConverter;
import pl.jakubpiecuch.trainingmanager.service.flow.FlowManager;
import pl.jakubpiecuch.trainingmanager.service.user.authentication.AuthenticationService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;

/**
 * Created by Rico on 2014-12-31.
 */
public class PlanConverter extends AbstractFlowConverter<PlanDto, Plan> {

    private AuthenticationService authenticationService;
    private FlowManager<PlanDto> planManager;

    @Override
    protected PlanDto convertTo(Plan entity, boolean full) {

        PlanDto flow = new PlanDto();

        flow.setId(entity.getId());
        flow.setName(entity.getName());
        flow.setGoal(entity.getGoal());
        flow.setCreatorId(entity.getCreator().getId());
        flow.setEditable(!entity.getUsed() && authenticationService.signed().getId() == entity.getCreator().getId());
        flow.setPhases(full ? manager.children(entity.getId(), true) : null);
        flow.setUsed(entity.getUsed());
        return flow;
    }

    @Override
    protected Plan convertFrom(PlanDto flowObject) {
        Plan entity = new Plan();

        entity.setName(flowObject.getName());
        entity.setGoal(flowObject.getGoal());
        if (flowObject.getId() == null) {
            Authentication signed = authenticationService.signed();
            entity.setCreator(new Account(signed.getId()));
        } else {
            PlanDto persisted = planManager.retrieve(flowObject.getId(), false);
            entity.setId(flowObject.getId());
            entity.setCreator(new Account(persisted.getCreatorId()));
        }
        entity.setUsed(flowObject.getUsed());
        return entity;
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void setPlanManager(FlowManager planManager) {
        this.planManager = planManager;
    }
}
