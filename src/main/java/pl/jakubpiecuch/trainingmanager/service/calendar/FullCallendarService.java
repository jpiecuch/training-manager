package pl.jakubpiecuch.trainingmanager.service.calendar;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.text.SimpleDateFormat;
import java.util.List;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jakubpiecuch.trainingmanager.dao.DayExercisesDao;
import pl.jakubpiecuch.trainingmanager.dao.ExercisesDao;
import pl.jakubpiecuch.trainingmanager.domain.Calendars;
import pl.jakubpiecuch.trainingmanager.domain.Exercises;
import pl.jakubpiecuch.trainingmanager.domain.DayExercises;
import pl.jakubpiecuch.trainingmanager.domain.Users;

@Service
public class FullCallendarService implements CalendarService {
    private final static String CALENDAR_FORMAT_DATE = "yyyy-MM-dd";

    private DayExercisesDao dayExercisesDao;
    private ExercisesDao exercisesDao;

    @Override
    public  List<Event> events(Users user) {
        return Lists.newArrayList(Lists.transform(dayExercisesDao.findByCalendarId(user.getCalendar().getId()), new Function<DayExercises, Event>() {
            @Override
            public Event apply(DayExercises d) {
                Event result = new Event();
                result.setAllDay(Boolean.TRUE);
                result.setId(d.getId());
                result.setStart(DateFormatUtils.format(d.getDate(), CALENDAR_FORMAT_DATE));
                result.setTitle(d.getExercise().getName());
                return result;
            } 
        }));
    }

    @Override
    public Event create(Event event, Users user) throws Exception {
        DayExercises d = new DayExercises();
        d.setDate(DateUtils.parseDate(event.getStart(), new String[] {CALENDAR_FORMAT_DATE}));
        d.setExercise(new Exercises(event.getId()));
        d.setCalendar(new Calendars(user.getCalendar().getId()));
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
            event.setTitle(last.getExercise().getName());
        } else {
            d.setSeries(DayExercises.DEFAULT_SERIES);
            event.setTitle(exercisesDao.findById(event.getId()).getName());
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
        DayExercises dayExercise = new DayExercises();
        dayExercise.setId(event.getId());
        dayExercisesDao.delete(dayExercise);
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
