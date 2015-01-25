package pl.jakubpiecuch.trainingmanager.domain;

import org.apache.commons.lang.StringUtils;
import pl.jakubpiecuch.trainingmanager.web.util.WebUtil;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "workout")
public class Workout extends CommonEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String MUSCLE_DELIMITER = ";";

    public enum WeekDay { SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY }

    private WeekDay weekDay;
    private Phase phase;
    private Integer position;
    private String muscle;

    public Workout() {
    }

    public Workout(Long id) {
        super(id);
    }

    @Column(name = "position")
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Column(name = "week_day")
    @Enumerated(EnumType.ORDINAL)
    public WeekDay getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phase")
    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    @Column(name = "muscles")
    protected String getMuscle() {
        return muscle;
    }

    protected void setMuscle(String muscles) {
        this.muscle = muscles;
    }

    @Transient
    public Description.Muscles[] getMuscles() {
        Integer[] ids = WebUtil.toIntArray(StringUtils.splitByWholeSeparatorPreserveAllTokens(muscle, MUSCLE_DELIMITER));
        Description.Muscles[] result = new Description.Muscles[ids.length];
        for(int i = 0; i < ids.length; i++) {
            result[i] = Description.Muscles.values()[ids[i]];
        }
        return result;
    }

    public void setMuscles(Description.Muscles[] muscles) {
        Integer[] ids = new Integer[muscles.length];
        for(int i = 0; i < muscles.length; i++) {
            ids[i] = muscles[i].ordinal();
        }
        this.muscle = StringUtils.join(ids, MUSCLE_DELIMITER);
    }
}
