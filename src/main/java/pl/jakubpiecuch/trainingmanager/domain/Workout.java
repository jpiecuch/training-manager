package pl.jakubpiecuch.trainingmanager.domain;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workout")
public class Workout extends CommonEntity implements Serializable {
    public static final String PHASE_FIELD = "phase";
    public static final String WEEK_DAY_FIELD = "weekDay";
    public static final String MUSCLES_FIELD = "muscles";
    private static final long serialVersionUID = 1L;
    private static final String MUSCLE_DELIMITER = ";";
    private WeekDay weekDay;
    private Phase phase;
    private String muscle;
    private List<Exercise> exercises = new ArrayList<Exercise>();
    public Workout() {
        super();
    }

    public Workout(Long id) {
        super(id);
    }

    @Column(name = "week_day")
    @Enumerated(EnumType.ORDINAL)
    @NotNull
    public WeekDay getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = PHASE_FIELD)
    @NotNull
    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    @Column(name = MUSCLES_FIELD)
    @NotNull
    protected String getMuscle() {
        return muscle;
    }

    protected void setMuscle(String muscles) {
        this.muscle = muscles;
    }

    @OneToMany(mappedBy = Exercise.WORKOUT_FIELD, cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Transient
    public Description.Muscles[] getMuscles() {
        Integer[] ids = WebUtil.toIntArray(StringUtils.splitByWholeSeparatorPreserveAllTokens(muscle, MUSCLE_DELIMITER));
        Description.Muscles[] result = new Description.Muscles[ids.length];
        for (int i = 0; i < ids.length; i++) {
            result[i] = Description.Muscles.values()[ids[i]];
        }
        return result;
    }

    public void setMuscles(Description.Muscles[] muscles) {
        Integer[] ids = new Integer[muscles.length];
        for (int i = 0; i < muscles.length; i++) {
            ids[i] = muscles[i].ordinal();
        }
        this.muscle = StringUtils.join(ids, MUSCLE_DELIMITER);
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
        Workout rhs = (Workout) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.weekDay, rhs.weekDay)
                .append(this.phase, rhs.phase)
                .append(this.muscle, rhs.muscle)
                .append(this.exercises, rhs.exercises)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(weekDay)
                .append(phase)
                .append(muscle)
                .append(exercises)
                .toHashCode();
    }

    public enum WeekDay {
        SUNDAY(7), MONDAY(1), TUESDAY(2), WEDNESDAY(3), THURSDAY(4), FRIDAY(5), SATURDAY(6);

        private int dayInWeek;

        WeekDay(int dayInWeek) {
            this.dayInWeek = dayInWeek;
        }

        public int getDayInWeek() {
            return dayInWeek;
        }
    }
}
