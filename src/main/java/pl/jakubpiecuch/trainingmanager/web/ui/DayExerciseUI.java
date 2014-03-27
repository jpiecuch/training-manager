package pl.jakubpiecuch.trainingmanager.web.ui;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.springframework.context.MessageSource;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Bars;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Benches;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Dumbbells;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Exercises;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Loads;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Necks;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Press;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Stands;
import pl.jakubpiecuch.trainingmanager.domain.plans.Calendars;
import pl.jakubpiecuch.trainingmanager.domain.plans.DayExercises;


public class DayExerciseUI implements Serializable {
    private Long id;
    private Date date;
    private Long calendarId;
    private ExerciseUI exercise;
    private SeriesUI[] series;
    private Integer position;
    private List<Benches> benches;
    private List<Dumbbells> dumbbells;
    private List<Loads> loads;
    private List<Necks> necks;
    private List<Stands> stands;
    private List<Bars> bars;
    private List<Press> press;
    private Double totalWeight;
    private Boolean confirmed;

    public static DayExerciseUI fromDayExercise(final DayExercises d, MessageSource messageSource, Locale locale) {
        DayExerciseUI result = new DayExerciseUI();
        result.id = d.getId();
        result.exercise = ExerciseUI.fromExercise(d.getExercise(), messageSource, locale);
        String[] seriesArray = d.getSeries().split(DayExercises.SERIES_DELIMITER);
        SeriesUI[] series = new SeriesUI[seriesArray.length];
        for(int i = 0; i < seriesArray.length; i++) {
            series[i] = new SeriesUI(seriesArray[i]);
        }
        result.series = series;
        result.position = d.getPosition();
        result.benches = Lists.newArrayList(d.getBenches());
        result.dumbbells = Lists.newArrayList(d.getDumbbells());
        result.loads = Lists.newArrayList(d.getLoads());
        result.necks = Lists.newArrayList(d.getNecks());
        result.stands = Lists.newArrayList(d.getStands());
        result.bars = Lists.newArrayList(d.getBars());
        result.press = Lists.newArrayList(d.getPress());
        result.date = d.getDate();
        result.totalWeight = d.getTotalWeight();
        result.calendarId = d.getCalendar().getId();
        result.confirmed = d.getConfirmed();
        return result;
    }
    
    public static List<DayExerciseUI> fromDayExerciseList(List<DayExercises> list, final MessageSource messageSource, final Locale locale) {
        return Lists.newArrayList(Lists.transform(list, new Function<DayExercises, DayExerciseUI>() {
            @Override
            public DayExerciseUI apply(DayExercises d) {
                return fromDayExercise(d, messageSource, locale);
            }
        }));
    }

    public Long getId() {
        return id;
    }

    public ExerciseUI getExercise() {
        return exercise;
    }

    public SeriesUI[] getSeries() {
        return series;
    }

    public Integer getPosition() {
        return position;
    }

    public List<Benches> getBenches() {
        return benches;
    }

    public List<Dumbbells> getDumbbells() {
        return dumbbells;
    }

    public List<Loads> getLoads() {
        return loads;
    }

    public List<Necks> getNecks() {
        return necks;
    }

    public List<Stands> getStands() {
        return stands;
    }

    public List<Bars> getBars() {
        return bars;
    }

    public Date getDate() {
        return date;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public Long getCalendarId() {
        return calendarId;
    }
    
    public Boolean getConfirmed() {
        return confirmed;
    }

    public List<Press> getPress() {
        return press;
    }
    
    public DayExercises toDayExercises() {
        DayExercises result = new DayExercises(this.id);
        result.setPosition(this.position);
        result.setDate(this.date);
        result.setConfirmed(this.confirmed);
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < this.series.length;) {
            builder.append(series[i++].getValue());
            if (i < series.length) {
                builder.append(DayExercises.SERIES_DELIMITER);
            }        
        }
        result.setSeries(builder.toString());
        result.setBars(Sets.newHashSet(this.bars));
        result.setBenches(Sets.newHashSet(this.benches));
        result.setDumbbells(Sets.newHashSet(this.dumbbells));
        result.setLoads(Sets.newHashSet(this.loads));
        result.setNecks(Sets.newHashSet(this.necks));
        result.setStands(Sets.newHashSet(this.stands));
        result.setPress(Sets.newHashSet(this.press));
        result.setCalendar(new Calendars(this.calendarId));
        result.setExercise(new Exercises(this.exercise.getId()));
        return result;
    }
    
}
