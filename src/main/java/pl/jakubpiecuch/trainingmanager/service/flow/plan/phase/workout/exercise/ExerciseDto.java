package pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout.exercise;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.service.flow.Flow;

/**
 * Created by Rico on 2015-01-06.
 */
public class ExerciseDto extends Flow {

    private Long id;
    private Long workoutId;
    private Long descriptionId;
    private Integer[] sets;
    private Description description;
    private Integer position;
    private Integer group;

    public Long getId() {
        return id;
    }

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

    public Integer[] getSets() {
        return sets;
    }

    public void setSets(Integer[] sets) {
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

    @Override
    @JsonIgnore
    public Hierarchy getHierarchy() {
        return Hierarchy.EXERCISE;
    }
}
