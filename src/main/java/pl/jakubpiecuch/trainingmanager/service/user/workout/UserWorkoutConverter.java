package pl.jakubpiecuch.trainingmanager.service.user.workout;

import org.springframework.transaction.annotation.Transactional;
import pl.jakubpiecuch.trainingmanager.dao.ExecutionDao;
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
    private ExecutionDao executionDao;

    @Override
    @Transactional
    public UserWorkoutDto fromEntity(UserWorkout entity, boolean full) {
        UserWorkoutDto result = new UserWorkoutDto();
        result.setId(entity.getId());
        result.setDate(entity.getDate());
        result.setMuscles(entity.getWorkout().getMuscles());
        result.setPlan((PlanDto) planConverter.fromEntity(entity.getWorkout().getPhase().getPlan(), false));
        result.setPhase((PhaseDto) phaseConverter.fromEntity(entity.getWorkout().getPhase(), false));
        result.setExecutions(full ? executionConverter.fromEntityList(executionDao.findByParentId(entity.getId()), full) : null);
        result.setWeekDay(entity.getWorkout().getWeekDay());
        return result;
    }

    @Override
    public UserWorkout toEntity(UserWorkoutDto object) {
        throw new UnsupportedOperationException();
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

    public void setExecutionDao(ExecutionDao executionDao) {
        this.executionDao = executionDao;
    }
}
