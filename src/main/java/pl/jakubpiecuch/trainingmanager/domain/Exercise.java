package pl.jakubpiecuch.trainingmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.commons.lang.ArrayUtils;
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
    public Integer[] getSets() {
        return WebUtil.toIntArray(StringUtils.splitByWholeSeparatorPreserveAllTokens(reps, SET_DELIMITER));
    }

    public void setSets(Integer[] sets) {
        this.reps = StringUtils.join(sets, SET_DELIMITER);
    }
}
