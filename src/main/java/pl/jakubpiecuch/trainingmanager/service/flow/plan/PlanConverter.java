package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import pl.jakubpiecuch.trainingmanager.dao.PlanDao;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.domain.Phase;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.converter.AbstractConverter;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.PhaseConverter;
import pl.jakubpiecuch.trainingmanager.service.user.authentication.AuthenticationService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;

import java.util.ArrayList;

/**
 * Created by Rico on 2014-12-31.
 */
public class PlanConverter extends AbstractConverter<PlanDto, Plan> {

    private AuthenticationService authenticationService;
    private PlanDao planDao;
    private PhaseConverter phaseConverter;

    @Override
    protected PlanDto convertTo(Plan entity) {

        PlanDto flow = new PlanDto();

        flow.setId(entity.getId());
        flow.setName(entity.getName());
        flow.setGoal(entity.getGoal());
        flow.setCreatorId(entity.getCreator().getId());
        flow.setEditable(!entity.getUsed() && authenticationService.signed().getId() == entity.getCreator().getId());
        flow.setPhases(phaseConverter.fromEntities(entity.getPhases()));
        flow.setUsed(entity.getUsed());
        return flow;
    }

    @Override
    protected Plan convertFrom(PlanDto dto, Plan entity) {
        entity.setName(dto.getName());
        entity.setGoal(dto.getGoal());
        if (dto.getId() == null) {
            Authentication signed = authenticationService.signed();
            entity.setCreator(new Account(signed.getId()));
        }
        entity.setPhases(new ArrayList<Phase>());
        for (Phase phase : phaseConverter.toEntities(dto.getPhases(), entity.getPhases())) {
            phase.setPlan(entity);
            entity.getPhases().add(phase);
        }

        return entity;
    }

    @Override
    protected Plan getEmpty() {
        return new Plan();
    }

    public void setAuthenticationService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void setPhaseConverter(PhaseConverter phaseConverter) {
        this.phaseConverter = phaseConverter;
    }

    public void setPlanDao(PlanDao planDao) {
        this.planDao = planDao;
    }
}
