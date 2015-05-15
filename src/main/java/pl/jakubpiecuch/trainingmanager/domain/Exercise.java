package pl.jakubpiecuch.trainingmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.StringUtils;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

import javax.persistence.*;

@Entity
@Table(name = "exercise")
public class Exercise extends CommonEntity {

    private static final String SET_DELIMITER = ";";
    public static final String FAIL_KEY = "FAIL";

    private Description description;
    private Workout workout;
    private String reps;
    private Integer position;
    private Integer group;

    public Exercise() {
    }

    public Exercise(Long id) {
        super(id);
    }

    @ManyToOne(fetch = FetchType.EAGER)
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

    @Column(name = "super_set")
    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    @Column(name = "position")
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Transient
    public String[] getSets() {
        return StringUtils.splitByWholeSeparatorPreserveAllTokens(reps, SET_DELIMITER);
    }

    public void setSets(String[] sets) {
        this.reps = StringUtils.join(sets, SET_DELIMITER);
    }
}
