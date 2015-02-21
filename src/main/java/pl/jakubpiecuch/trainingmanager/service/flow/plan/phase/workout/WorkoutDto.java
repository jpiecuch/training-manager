package pl.jakubpiecuch.trainingmanager.service.flow.plan.phase.workout;

import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.domain.Workout;
import pl.jakubpiecuch.trainingmanager.service.flow.Flow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rico on 2015-01-06.
 */
public class WorkoutDto extends Flow {

    private Long id;
    private Description.Muscles[] muscles;
    private Workout.WeekDay weekDay;
    private Integer position;
    private Long phaseId;
    private List<GroupDto> groups = new ArrayList<GroupDto>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Description.Muscles[] getMuscles() {
        return muscles;
    }

    public void setMuscles(Description.Muscles[] muscles) {
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

    public List<GroupDto> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupDto> groups) {
        this.groups = groups;
    }

    @Override
    public Hierarchy getHierarchy() {
        return Hierarchy.WORKOUT;
    }
}
