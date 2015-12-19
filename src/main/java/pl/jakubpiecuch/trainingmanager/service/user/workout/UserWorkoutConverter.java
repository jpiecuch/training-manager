package pl.jakubpiecuch.trainingmanager.service.user.workout;

import org.springframework.transaction.annotation.Transactional;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.domain.Phase;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.domain.UserWorkout;
import pl.jakubpiecuch.trainingmanager.service.converter.AbstractConverter;
import pl.jakubpiecuch.trainingmanager.service.converter.Converter;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.PlanDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.PhaseDto;
import pl.jakubpiecuch.trainingmanager.service.user.workout.session.UserWorkoutCriteria;

/**
 * Created by Rico on 2015-01-18.
 */
public class UserWorkoutConverter extends AbstractConverter<UserWorkoutDto, UserWorkout> {

    private Converter executionConverter;
    private RepoDao<UserWorkout, UserWorkoutCriteria> userWorkoutDao;

    @Override
    protected UserWorkout convertFrom(UserWorkoutDto dto, UserWorkout entity) {
        entity = userWorkoutDao.findById(dto.getId());
        entity.setComment(dto.getComment());
        entity.setState(dto.getState());
        return entity;
    }

    @Override
    @Transactional
    protected UserWorkoutDto convertTo(UserWorkout entity) {
        UserWorkoutDto result = new UserWorkoutDto();
        result.setId(entity.getId());
        result.setDate(entity.getDate());
        result.setMuscles(entity.getWorkout().getMuscles());
        result.setPlan(getPlan(entity.getWorkout().getPhase().getPlan()));
        result.setPhase(getPhase(entity.getWorkout().getPhase()));
        result.setExecutions(executionConverter.fromEntities(entity.getExecutions()));
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

    public void setUserWorkoutDao(RepoDao<UserWorkout, UserWorkoutCriteria> userWorkoutDao) {
        this.userWorkoutDao = userWorkoutDao;
    }
}
