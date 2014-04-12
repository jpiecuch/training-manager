package pl.jakubpiecuch.trainingmanager.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "day_exercises")
public class DayExercises extends pl.jakubpiecuch.trainingmanager.domain.CommonEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String SERIES_DELIMITER = ";";
    public static final String DEFAULT_SERIES = "0;0;0";

    private Exercises exercise;
    private String series;
    private Date date;
    private Integer position;
    private Boolean confirmed;
    private Calendars calendar;
    private Set<Benches> benches;
    private Set<Dumbbells> dumbbells;
    private Set<Loads> loads;
    private Set<Necks> necks;
    private Set<Stands> stands;
    private Set<Bars> bars;
    private Set<Press> press;

    public DayExercises(Long id) {
        super(id);
    }

    public DayExercises() {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type")
    public Exercises getExercise() {
        return exercise;
    }

    public void setExercise(Exercises exercise) {
        this.exercise = exercise;
    }

    @Column(name = "series")
    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "exercise_benches", joinColumns = @JoinColumn(name = "exercise"), inverseJoinColumns = @JoinColumn(name = "equipment"))
    public Set<Benches> getBenches() {
        return benches;
    }

    public void setBenches(Set<Benches> benches) {
        this.benches = benches;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "exercise_dumbbells", joinColumns = @JoinColumn(name = "exercise"), inverseJoinColumns = @JoinColumn(name = "equipment"))
    public Set<Dumbbells> getDumbbells() {
        return dumbbells;
    }

    public void setDumbbells(Set<Dumbbells> dumbbells) {
        this.dumbbells = dumbbells;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "exercise_loads", joinColumns = @JoinColumn(name = "exercise"), inverseJoinColumns = @JoinColumn(name = "equipment"))
    public Set<Loads> getLoads() {
        return loads;
    }

    public void setLoads(Set<Loads> loads) {
        this.loads = loads;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "exercise_necks", joinColumns = @JoinColumn(name = "exercise"), inverseJoinColumns = @JoinColumn(name = "equipment"))
    public Set<Necks> getNecks() {
        return necks;
    }

    public void setNecks(Set<Necks> necks) {
        this.necks = necks;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "exercise_stands", joinColumns = @JoinColumn(name = "exercise"), inverseJoinColumns = @JoinColumn(name = "equipment"))
    public Set<Stands> getStands() {
        return stands;
    }

    public void setStands(Set<Stands> standsList) {
        this.stands = standsList;
    }

    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "position")
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "calendar")
    public Calendars getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendars calendar) {
        this.calendar = calendar;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "exercise_bars", joinColumns = @JoinColumn(name = "exercise"), inverseJoinColumns = @JoinColumn(name = "equipment"))
    public Set<Bars> getBars() {
        return bars;
    }

    public void setBars(Set<Bars> bars) {
        this.bars = bars;
    }

    @Column(name = "confirmed")
    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "exercise_press", joinColumns = @JoinColumn(name = "exercise"), inverseJoinColumns = @JoinColumn(name = "equipment"))
    public Set<Press> getPress() {
        return press;
    }

    public void setPress(Set<Press> press) {
        this.press = press;
    }

    @Transient
    public Double getTotalWeight() {
        Double result = 0.0;
        for (Dumbbells d : this.dumbbells) {
            result += d.getWeight();
        }
        for (Loads l : this.loads) {
            result += l.getWeight();
        }
        for (Necks n : this.necks) {
            result += n.getWeight();
        }
        return result;
    }
}
