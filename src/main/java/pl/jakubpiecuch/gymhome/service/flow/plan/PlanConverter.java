package pl.jakubpiecuch.gymhome.service.flow.plan;

import pl.jakubpiecuch.gymhome.domain.Account;
import pl.jakubpiecuch.gymhome.domain.Phase;
import pl.jakubpiecuch.gymhome.domain.Plan;
import pl.jakubpiecuch.gymhome.service.converter.AbstractConverter;
import pl.jakubpiecuch.gymhome.service.flow.plan.phase.PhaseConverter;
import pl.jakubpiecuch.gymhome.service.flow.plan.phase.PhaseDto;
import pl.jakubpiecuch.gymhome.service.identify.IdentifyObject;
import pl.jakubpiecuch.gymhome.service.user.authentication.AuthenticationService;
import pl.jakubpiecuch.gymhome.service.user.model.Authentication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Rico on 2014-12-31.
 */
public class PlanConverter extends AbstractConverter<PlanDto, Plan> {

    private AuthenticationService authenticationService;
    private PhaseConverter phaseConverter;

    @Override
    protected PlanDto convertTo(Plan entity) {

        PlanDto flow = new PlanDto();

        flow.setId(entity.getId());
        flow.setName(entity.getName());
        flow.setGoal(entity.getGoal());
        flow.setCreatorId(entity.getCreator().getId());
        flow.setEditable(!entity.isUsed() && authenticationService.signed().getId() == entity.getCreator().getId());
        flow.setPhases(phaseConverter.fromEntities(entity.getPhases()));
        flow.setUsed(entity.isUsed());
        return flow;
    }

    @Override
    protected Plan convertFrom(PlanDto dto, Plan entity) {
        entity.setName(dto.getName());
        entity.setGoal(dto.getGoal());
        entity.setUsed(entity.isUsed() || dto.isUsed());
        if (dto.getId() == null) {
            Authentication signed = authenticationService.signed();
            entity.setCreator(new Account(signed.getId()));
        }

        Map<Long, ? extends IdentifyObject> map = uniqueMap(dto.getPhases());

        List<Phase> phases = new ArrayList<Phase>();
        for (Phase phase : entity.getPhases()) {
            if (map.containsKey(phase.getId())) {
                Phase e = phaseConverter.toEntity((PhaseDto) map.get(phase.getId()), phase);
                e.setPlan(entity);
                phases.add(e);
            }
        }

        Collection<? extends IdentifyObject> newPhases = filterNew(dto.getPhases());

        for (IdentifyObject phase : newPhases) {
            Phase e = phaseConverter.toEntity((PhaseDto) phase, null);
            e.setPlan(entity);
            phases.add(e);
        }
        entity.getPhases().clear();
        entity.getPhases().addAll(phases);
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
}
