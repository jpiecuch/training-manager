package pl.jakubpiecuch.trainingmanager.service.user.workout;

import pl.jakubpiecuch.trainingmanager.domain.Execution;
import pl.jakubpiecuch.trainingmanager.domain.UserWorkout;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.exercise.ExerciseDto;
import pl.jakubpiecuch.trainingmanager.service.repository.RepoObject;

import java.util.List;

/**
 * Created by Rico on 2015-01-18.
 */
public class ExecutionDto implements RepoObject {

    private Long id;
    private ExerciseDto exercise;
    private String comment;
    private UserWorkout.State state;
    private List<Execution.Result> results;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public ExerciseDto getExercise() {
        return exercise;
    }

    public void setExercise(ExerciseDto exercise) {
        this.exercise = exercise;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UserWorkout.State getState() {
        return state;
    }

    public void setState(UserWorkout.State state) {
        this.state = state;
    }

    public List<Execution.Result> getResults() {
        return results;
    }

    public void setResults(List<Execution.Result> results) {
        this.results = results;
    }
}
