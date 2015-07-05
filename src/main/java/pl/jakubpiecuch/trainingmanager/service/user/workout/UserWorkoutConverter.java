package pl.jakubpiecuch.trainingmanager.service.user.workout;

import org.springframework.transaction.annotation.Transactional;
import pl.jakubpiecuch.trainingmanager.dao.ExecutionDao;
import pl.jakubpiecuch.trainingmanager.dao.UserWorkoutDao;
import pl.jakubpiecuch.trainingmanager.domain.UserWorkout;
import pl.jakubpiecuch.trainingmanager.service.converter.AbstractConverter;
import pl.jakubpiecuch.trainingmanager.service.converter.Converter;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.PlanDto;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.PhaseDto;

/**
 * Created by Rico on 2015-01-18.
 */
public class UserWorkoutConverter extends AbstractConverter<UserWorkoutDto, UserWorkout> {

    private Converter planConverter;
    private Converter phaseConverter;
    private Converter executionConverter;
    private UserWorkoutDao userWorkoutDao;

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
        result.setPlan((PlanDto) planConverter.fromEntity(entity.getWorkout().getPhase().getPlan()));
        result.setPhase((PhaseDto) phaseConverter.fromEntity(entity.getWorkout().getPhase()));
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

    public void setPlanConverter(Converter planConverter) {
        this.planConverter = planConverter;
    }

    public void setPhaseConverter(Converter phaseConverter) {
        this.phaseConverter = phaseConverter;
    }

    public void setExecutionConverter(Converter executionConverter) {
        this.executionConverter = executionConverter;
    }

    public void setUserWorkoutDao(UserWorkoutDao userWorkoutDao) {
        this.userWorkoutDao = userWorkoutDao;
    }
}
