package pl.jakubpiecuch.trainingmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "plan")
public class Plan extends pl.jakubpiecuch.trainingmanager.domain.CommonEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Goal {muscles, strength};

    private String name;
    private Integer weeks;
    private Goal goal;
    private Account creator;
    private List<PlanExercise> exercises;

    public Plan() {
    }

    public Plan(Long id) {
        super(id);
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "goal")
    @Enumerated(EnumType.ORDINAL)
    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    @Column(name = "weeks")
    public Integer getWeeks() {
        return weeks;
    }

    public void setWeeks(Integer weeks) {
        this.weeks = weeks;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator")
    public Account getCreator() {
        return creator;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "plan")
    @JsonIgnore
    @OrderBy(value = "position asc")
    public List<PlanExercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<PlanExercise> exercises) {
        this.exercises = exercises;
    }

    @Transient
    public List<PlanExercise> getMonday() {
        return filter(PlanExercise.WeekDay.MONDAY);
    }

    @Transient
    public List<PlanExercise> getTuesday() {
        return filter(PlanExercise.WeekDay.TUESDAY);
    }

    @Transient
    public List<PlanExercise> getWednesday() {
        return filter(PlanExercise.WeekDay.WEDNESDAY);
    }

    @Transient
    public List<PlanExercise> getThursday() {
        return filter(PlanExercise.WeekDay.THURSDAY);
    }

    @Transient
    public List<PlanExercise> getFriday() {
        return filter(PlanExercise.WeekDay.FRIDAY);
    }

    @Transient
    public List<PlanExercise> getSaturday() {
        return filter(PlanExercise.WeekDay.WEDNESDAY);
    }

    @Transient
    public List<PlanExercise> getSunday() {
        return filter(PlanExercise.WeekDay.WEDNESDAY);
    }

    private  List<PlanExercise> filter(final PlanExercise.WeekDay weekDay) {
        return Lists.newArrayList(Iterables.filter(exercises, new Predicate<PlanExercise>() {
            @Override
            public boolean apply(PlanExercise planExercise) {
                return planExercise.getWeekDay() == weekDay;
            }
        }));
    }

}
