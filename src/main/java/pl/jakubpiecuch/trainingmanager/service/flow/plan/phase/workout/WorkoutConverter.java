package pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout;

import com.google.common.collect.Lists;
import pl.jakubpiecuch.trainingmanager.domain.Phase;
import pl.jakubpiecuch.trainingmanager.domain.Workout;
import pl.jakubpiecuch.trainingmanager.service.flow.AbstractFlowConverter;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.exercise.ExerciseDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rico on 2014-12-31.
 */
public class WorkoutConverter extends AbstractFlowConverter<WorkoutDto, Workout> {

    @Override
    protected Workout convertFrom(WorkoutDto flowObject) {
        Workout entity = new Workout();

        entity.setId(flowObject.getId());
        entity.setMuscles(flowObject.getMuscles());
        entity.setPhase(new Phase(flowObject.getPhaseId()));
        entity.setPosition(flowObject.getPosition());
        entity.setWeekDay(flowObject.getWeekDay());

        return entity;
    }

    @Override
    protected WorkoutDto convertTo(Workout entity, boolean full) {
        WorkoutDto dto = new WorkoutDto();

        dto.setId(entity.getId());
        dto.setPosition(entity.getPosition());
        dto.setWeekDay(entity.getWeekDay());
        dto.setMuscles(entity.getMuscles());
        dto.setPhaseId(entity.getPhase().getId());
        List<ExerciseDto> exercises = full ? manager.children(entity.getId(), full) : null;
        Map<Integer, List<ExerciseDto>> map = new HashMap<Integer, List<ExerciseDto>>();
        if (exercises != null) {
            for (ExerciseDto exerciseDto : exercises) {
                if (map.containsKey(exerciseDto.getGroup())) {
                    map.get(exerciseDto.getGroup()).add(exerciseDto);
                } else {
                    map.put(exerciseDto.getGroup(), Lists.newArrayList(exerciseDto));
                }
            }
        }
        for (Map.Entry<Integer, List<ExerciseDto>> entry : map.entrySet()) {
            GroupDto group = new GroupDto();
            group.setExercises(entry.getValue());
            group.setId(entry.getKey());
            dto.getGroups().add(group);
        }
        return dto;
    }
}
