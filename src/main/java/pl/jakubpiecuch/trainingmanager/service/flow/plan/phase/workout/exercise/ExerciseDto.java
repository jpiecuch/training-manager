package pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.exercise;

import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;
import pl.jakubpiecuch.trainingmanager.service.identify.IdentifyObject;

/**
 * Created by Rico on 2015-01-06.
 */
public class ExerciseDto implements IdentifyObject {

    public static final String DESCRIPTION_ID = Exercise.DESCRIPTION_FIELD + "Id";
    public static final String SETS_FIELD = "sets";
    private Long id;
    private Long workoutId;
    private Long descriptionId;
    private String[] sets;
    private Description description;
    private Integer position;
    private Integer group;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkoutId() {
        return workoutId;
    }

    public void setWorkoutId(Long workoutId) {
        this.workoutId = workoutId;
    }

    public Long getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(Long descriptionId) {
        this.descriptionId = descriptionId;
    }

    public String[] getSets() {
        return sets;
    }

    public void setSets(String[] sets) {
        this.sets = sets;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}
