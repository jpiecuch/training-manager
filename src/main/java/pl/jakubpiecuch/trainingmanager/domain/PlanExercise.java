package pl.jakubpiecuch.trainingmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang.StringUtils;
import pl.jakubpiecuch.trainingmanager.common.fasterxml.jackson.MapDeserializer;
import pl.jakubpiecuch.trainingmanager.common.util.ArrayMap;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "plan_exercise")
public class PlanExercise extends CommonEntity {

    private static final String SET_DELIMITER = ";";

    public enum WeekDay { SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY }

    private Exercise exercise;
    private Plan plan;
    private WeekDay weekDay;
    private int position;
    private String reps;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise")
    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan")
    @JsonIgnore
    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    @Column(name = "week_day")
    @Enumerated(EnumType.ORDINAL)
    public WeekDay getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    @Column(name = "position")
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Column(name = "reps")
    protected String getReps() {
        return this.reps;
    }

    protected void setReps(String reps) {
        this.reps = reps;
    }

    @Transient
    public Map<Integer, Integer> getSets() {
        return new ArrayMap<Integer>(reps != null ? WebUtil.toIntArray(StringUtils.splitByWholeSeparatorPreserveAllTokens(reps, SET_DELIMITER)) : new Integer[0]) {
            @Override
            public void update(Integer[] array) {
                 reps = StringUtils.join(array, SET_DELIMITER);
            }
        };
    }

    @JsonDeserialize(using = MapDeserializer.class)
    protected void setSets(Map<Integer, Integer> sets) {
        for (Map.Entry<Integer, Integer> e : sets.entrySet()) {
            getSets().put(e.getKey(), e.getValue());
        }
    }
}
