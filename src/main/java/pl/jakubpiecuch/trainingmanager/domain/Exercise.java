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
@Table(name = "exercise")
public class Exercise extends CommonEntity {

    private static final String SET_DELIMITER = ";";

    private Description description;
    private Workout workout;
    private String reps;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "description")
    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout")
    @JsonIgnore
    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
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
