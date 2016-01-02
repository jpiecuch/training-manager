package pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout;

import com.google.common.collect.Lists;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;
import pl.jakubpiecuch.trainingmanager.domain.Phase;
import pl.jakubpiecuch.trainingmanager.domain.Workout;
import pl.jakubpiecuch.trainingmanager.service.converter.AbstractConverter;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.exercise.ExerciseConverter;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.exercise.ExerciseDto;
import pl.jakubpiecuch.trainingmanager.service.identify.IdentifyObject;

import java.util.*;

/**
 * Created by Rico on 2014-12-31.
 */
public class WorkoutConverter extends AbstractConverter<WorkoutDto, Workout> {

    private ExerciseConverter exerciseConverter;

    @Override
    protected Workout convertFrom(WorkoutDto dto, Workout entity) {
        entity.setId(dto.getId());
        entity.setMuscles(dto.getMuscles());
        entity.setPhase(new Phase(dto.getPhaseId()));
        entity.setWeekDay(dto.getWeekDay());
        List<ExerciseDto> exerciseDtos = new ArrayList<ExerciseDto>();
        for (GroupDto group : dto.getGroups()) {
            exerciseDtos.addAll(group.getExercises());
        }
        Map<Long, IdentifyObject> map = uniqueMap(exerciseDtos);

        List<Exercise> exercises = new ArrayList<Exercise>();
        for (Exercise exercise : entity.getExercises()) {
            if (map.containsKey(exercise.getId())) {
                Exercise e = exerciseConverter.toEntity((ExerciseDto) map.get(exercise.getId()), exercise);
                e.setWorkout(entity);
                exercises.add(e);
            }
        }

        Collection<? extends IdentifyObject> newWorkouts = filterNew(exerciseDtos);

        for (IdentifyObject exercise : newWorkouts) {
            Exercise e = exerciseConverter.toEntity((ExerciseDto) exercise, null);
            e.setWorkout(entity);
            exercises.add(e);
        }
        entity.getExercises().clear();
        entity.getExercises().addAll(exercises);
        return entity;
    }

    @Override
    protected WorkoutDto convertTo(Workout entity) {
        WorkoutDto dto = new WorkoutDto();

        dto.setId(entity.getId());
        dto.setWeekDay(entity.getWeekDay());
        dto.setMuscles(entity.getMuscles());
        dto.setPhaseId(entity.getPhase().getId());
        List<ExerciseDto> exercises = exerciseConverter.fromEntities(entity.getExercises());
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

    @Override
    protected Workout getEmpty() {
        return new Workout();
    }

    public void setExerciseConverter(ExerciseConverter exerciseConverter) {
        this.exerciseConverter = exerciseConverter;
    }
}
