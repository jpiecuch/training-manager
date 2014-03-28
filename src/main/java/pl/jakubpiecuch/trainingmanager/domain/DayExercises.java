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

@Entity
@Table(name = "day_exercises")
public class DayExercises extends pl.jakubpiecuch.trainingmanager.domain.Entity implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String SERIES_DELIMITER = ";";
    public static final String DEFAULT_SERIES = "0;0;0";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type")
    private Exercises exercise;
    @Column(name = "series")
    private String series;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "position")
    private Integer position;
    @Column(name = "confirmed")
    private Boolean confirmed;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "calendar")
    private Calendars calendar;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "exercise_benches", joinColumns = @JoinColumn(name = "exercise"), inverseJoinColumns = @JoinColumn(name = "equipment"))
    private Set<Benches> benches;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "exercise_dumbbells", joinColumns = @JoinColumn(name = "exercise"), inverseJoinColumns = @JoinColumn(name = "equipment"))
    private Set<Dumbbells> dumbbells;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "exercise_loads", joinColumns = @JoinColumn(name = "exercise"), inverseJoinColumns = @JoinColumn(name = "equipment"))
    private Set<Loads> loads;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "exercise_necks", joinColumns = @JoinColumn(name = "exercise"), inverseJoinColumns = @JoinColumn(name = "equipment"))
    private Set<Necks> necks;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "exercise_stands", joinColumns = @JoinColumn(name = "exercise"), inverseJoinColumns = @JoinColumn(name = "equipment"))
    private Set<Stands> stands;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "exercise_bars", joinColumns = @JoinColumn(name = "exercise"), inverseJoinColumns = @JoinColumn(name = "equipment"))
    private Set<Bars> bars;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "exercise_press", joinColumns = @JoinColumn(name = "exercise"), inverseJoinColumns = @JoinColumn(name = "equipment"))
    private Set<Press> press;

    public DayExercises(Long id) {
        super(id);
    }

    public DayExercises() {
    }

    public Exercises getExercise() {
        return exercise;
    }

    public void setExercise(Exercises exercise) {
        this.exercise = exercise;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Set<Benches> getBenches() {
        return benches;
    }

    public void setBenches(Set<Benches> benches) {
        this.benches = benches;
    }

    public Set<Dumbbells> getDumbbells() {
        return dumbbells;
    }

    public void setDumbbells(Set<Dumbbells> dumbbells) {
        this.dumbbells = dumbbells;
    }

    public Set<Loads> getLoads() {
        return loads;
    }

    public void setLoads(Set<Loads> loads) {
        this.loads = loads;
    }

    public Set<Necks> getNecks() {
        return necks;
    }

    public void setNecks(Set<Necks> necks) {
        this.necks = necks;
    }

    public Set<Stands> getStands() {
        return stands;
    }

    public void setStands(Set<Stands> standsList) {
        this.stands = standsList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Calendars getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendars calendar) {
        this.calendar = calendar;
    }

    public Set<Bars> getBars() {
        return bars;
    }

    public void setBars(Set<Bars> bars) {
        this.bars = bars;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Set<Press> getPress() {
        return press;
    }

    public void setPress(Set<Press> press) {
        this.press = press;
    }
    
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