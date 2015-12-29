package pl.jakubpiecuch.trainingmanager.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
@Table(name = "exercise")
public class Exercise extends CommonEntity {

    public static final String FAIL_KEY = "FAIL";
    public static final String WORKOUT_FIELD = "workout";
    public static final String DESCRIPTION_FIELD = "description";
    public static final String REPS_FIELD = "reps";
    public static final String SUPER_SET_FIELD = "super_set";
    public static final String POSITION_FIELD = "position";
    private static final String SET_DELIMITER = ";";
    private Description description;
    private Workout workout;
    private String reps;
    private Integer position;
    private Integer group;

    public Exercise() {
        super();
    }

    public Exercise(Long id) {
        super(id);
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = DESCRIPTION_FIELD)
    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = WORKOUT_FIELD)
    @JsonIgnore
    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    @Column(name = REPS_FIELD)
    protected String getReps() {
        return this.reps;
    }

    protected void setReps(String reps) {
        this.reps = reps;
    }

    @Column(name = SUPER_SET_FIELD)
    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    @Column(name = POSITION_FIELD)
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


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Exercise rhs = (Exercise) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.description, rhs.description)
                .append(this.workout, rhs.workout)
                .append(this.reps, rhs.reps)
                .append(this.position, rhs.position)
                .append(this.group, rhs.group)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(description)
                .append(workout)
                .append(reps)
                .append(position)
                .append(group)
                .toHashCode();
    }
}
