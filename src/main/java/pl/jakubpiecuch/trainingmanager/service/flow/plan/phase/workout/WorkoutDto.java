package pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout;

import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.domain.Workout;
import pl.jakubpiecuch.trainingmanager.service.flow.Flow;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.exercise.ExerciseDto;

import java.util.List;

/**
 * Created by Rico on 2015-01-06.
 */
public class WorkoutDto extends Flow {

    private Long id;
    private Description.PartyMuscles muscles;
    private Workout.WeekDay weekDay;
    private Integer position;
    private Long phaseId;
    private List<ExerciseDto> exercises;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Description.PartyMuscles getMuscles() {
        return muscles;
    }

    public void setMuscles(Description.PartyMuscles muscles) {
        this.muscles = muscles;
    }

    public Workout.WeekDay getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(Workout.WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Long getPhaseId() {
        return phaseId;
    }

    public void setPhaseId(Long phaseId) {
        this.phaseId = phaseId;
    }

    public List<ExerciseDto> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseDto> exercises) {
        this.exercises = exercises;
    }

    @Override
    public Hierarchy getHierarchy() {
        return Hierarchy.WORKOUT;
    }
}
