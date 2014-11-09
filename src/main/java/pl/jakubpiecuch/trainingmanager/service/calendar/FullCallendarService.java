package pl.jakubpiecuch.trainingmanager.service.calendar;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jakubpiecuch.trainingmanager.dao.DayExercisesDao;
import pl.jakubpiecuch.trainingmanager.dao.ExercisesDao;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.domain.DayExercises;
import pl.jakubpiecuch.trainingmanager.domain.Users;

@Service
public class FullCallendarService implements CalendarService {
    private final static String CALENDAR_FORMAT_DATE = "yyyy-MM-dd";
    private final static String EVENT_TITLE_FORMAT = "%s. %s";

    private DayExercisesDao dayExercisesDao;
    private ExercisesDao exercisesDao;

    @Override
    public  List<Event> events(Users user, Date start, Date end,final Locale locale) {
        return Lists.newArrayList(Lists.transform(dayExercisesDao.findByCalendarId(user.getCalendar().getId(), start, end), new Function<DayExercises, Event>() {
            @Override
            public Event apply(DayExercises d) {
                Event result = new Event();
                result.setAllDay(Boolean.TRUE);
                result.setId(d.getId());
                result.setStart(DateFormatUtils.format(d.getDate(), CALENDAR_FORMAT_DATE));
                result.setTitle(String.format(EVENT_TITLE_FORMAT, d.getPosition(), d.getExercise().getNames().get(locale.getLanguage())));
                return result;
            } 
        }));
    }

    @Override
    public Event create(Event event, Users user, Locale locale) throws Exception {
        DayExercises d = new DayExercises();
        d.setDate(DateUtils.parseDate(event.getStart(), new String[] {CALENDAR_FORMAT_DATE}));
        d.setExercise(new Exercise(event.getId()));
        d.setCalendar(new Plan(user.getCalendar().getId()));
        d.setPosition(dayExercisesDao.countByCalendarIdAndDate(user.getCalendar().getId(), d.getDate()).intValue() + 1);
        d.setConfirmed(false);
        DayExercises last = dayExercisesDao.findLastDayExercise(event.getId(), d.getDate());
        if (last != null) {
            d.setBars(Sets.newHashSet(last.getBars()));
            d.setBenches(Sets.newHashSet(last.getBenches()));
            d.setDumbbells(Sets.newHashSet(last.getDumbbells()));
            d.setLoads(Sets.newHashSet(last.getLoads()));
            d.setNecks(Sets.newHashSet(last.getNecks()));
            d.setStands(Sets.newHashSet(last.getStands()));
            d.setSeries(last.getSeries());
            event.setTitle(String.format(EVENT_TITLE_FORMAT, d.getPosition(), last.getExercise().getNames().get(locale.getLanguage())));
        } else {
            d.setSeries(DayExercises.DEFAULT_SERIES);
            event.setTitle(String.format(EVENT_TITLE_FORMAT, d.getPosition(), exercisesDao.findById(event.getId()).getNames().get(locale.getLanguage())));
        }
        dayExercisesDao.save(d);
        event.setId(d.getId());
        event.setAllDay(Boolean.TRUE);
        return event;
    }

    @Override
    public void move(Event event) throws Exception {
        DayExercises dayExercise = dayExercisesDao.findById(event.getId());
        dayExercise.setDate(new SimpleDateFormat(CALENDAR_FORMAT_DATE).parse(event.getStart()));
        dayExercisesDao.save(dayExercise);
    }

    @Override
    public void remove(Event event) {
        DayExercises dayExercise = dayExercisesDao.findById(event.getId());
        dayExercisesDao.delete(dayExercise);
        numeration(dayExercise.getCalendar().getId(), dayExercise.getDate());
    }
    
    private void numeration(Long calendarId, Date date) {
        List<DayExercises> exerciseses = dayExercisesDao.findByCalendarIdAndDate(calendarId, date);
        int i = 1;
        for (DayExercises d : exerciseses) {
            d.setPosition(i++);
            dayExercisesDao.save(d);
        }
    }

    @Autowired
    public void setDayExercisesDao(DayExercisesDao dayExercisesDao) {
        this.dayExercisesDao = dayExercisesDao;
    }

    @Autowired
    public void setExercisesDao(ExercisesDao exercisesDao) {
        this.exercisesDao = exercisesDao;
    }
}
