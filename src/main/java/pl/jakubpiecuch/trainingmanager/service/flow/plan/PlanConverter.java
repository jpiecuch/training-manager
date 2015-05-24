package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.springframework.transaction.annotation.Transactional;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.domain.Phase;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.converter.AbstractConverter;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.PhaseConverter;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.PhaseDto;
import pl.jakubpiecuch.trainingmanager.service.identify.IdentifyObject;
import pl.jakubpiecuch.trainingmanager.service.user.authentication.AuthenticationService;
import pl.jakubpiecuch.trainingmanager.service.user.model.Authentication;

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
            Phase e = phaseConverter.toEntity((PhaseDto)phase, null);
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
