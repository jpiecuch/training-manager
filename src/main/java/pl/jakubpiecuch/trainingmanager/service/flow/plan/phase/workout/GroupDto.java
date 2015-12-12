package pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout;

import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.exercise.ExerciseDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rico on 2015-02-15.
 */
public class GroupDto {
    private Integer id;
    private List<ExerciseDto> exercises = new ArrayList<ExerciseDto>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ExerciseDto> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseDto> exercises) {
        this.exercises = exercises;
    }
}
