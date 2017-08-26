package pl.jakubpiecuch.gymhome.service.user.workout;

import pl.jakubpiecuch.gymhome.domain.Phase;
import pl.jakubpiecuch.gymhome.domain.Plan;
import pl.jakubpiecuch.gymhome.domain.UserWorkout;
import pl.jakubpiecuch.gymhome.service.converter.AbstractConverter;
import pl.jakubpiecuch.gymhome.service.converter.Converter;
import pl.jakubpiecuch.gymhome.service.flow.plan.PlanDto;
import pl.jakubpiecuch.gymhome.service.flow.plan.phase.PhaseDto;

import java.util.Collections;

/**
 * Created by Rico on 2015-01-18.
 */
public class UserWorkoutConverter extends AbstractConverter<UserWorkoutDto, UserWorkout> {

    private Converter executionConverter;

    @Override
    protected UserWorkout convertFrom(UserWorkoutDto dto, UserWorkout entity) {
        entity.setComment(dto.getComment());
        entity.setState(dto.getState());
        return entity;
    }

    @Override
    protected UserWorkoutDto convertTo(UserWorkout entity) {
        UserWorkoutDto result = new UserWorkoutDto();
        result.setId(entity.getId());
        result.setDate(entity.getDate());
        result.setMuscles(entity.getWorkout().getMuscles());
        result.setPlan(getPlan(entity.getWorkout().getPhase().getPlan()));
        result.setPhase(getPhase(entity.getWorkout().getPhase()));
        result.setExecutions(executionConverter.fromEntities(entity.getExecutions()));
        Collections.sort(result.getExecutions(), (o1, o2) -> o1.getExercise().getPosition().compareTo(o2.getExercise().getPosition()));
        result.setWeekDay(entity.getWorkout().getWeekDay());
        result.setState(entity.getState());
        result.setComment(entity.getComment());
        return result;
    }

    @Override
    protected UserWorkout getEmpty() {
        return new UserWorkout();
    }

    private PlanDto getPlan(Plan plan) {
        PlanDto dto = new PlanDto();
        dto.setName(plan.getName());
        dto.setGoal(plan.getGoal());
        return dto;
    }

    private PhaseDto getPhase(Phase phase) {
        PhaseDto dto = new PhaseDto();
        dto.setDescription(phase.getDescription());
        dto.setGoal(phase.getGoal());
        dto.setPosition(phase.getPosition());
        return dto;
    }

    public void setExecutionConverter(Converter executionConverter) {
        this.executionConverter = executionConverter;
    }
}
