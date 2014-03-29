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
import pl.jakubpiecuch.trainingmanager.dao.CalendarsDao;
import pl.jakubpiecuch.trainingmanager.dao.DayExercisesDao;
import pl.jakubpiecuch.trainingmanager.domain.Exercises;
import pl.jakubpiecuch.trainingmanager.domain.Calendars;
import pl.jakubpiecuch.trainingmanager.domain.DayExercises;

@Service
public class FullCallendarService implements CalendarService {
    private final static String CALENDAR_FORMAT_DATE = "yyyy-MM-dd";

    private DayExercisesDao dayExercisesDao;
    private CalendarsDao calendarsDao;

    @Override
    public  List<Event> getEvents(Long userId) {
        return Lists.newArrayList(Lists.transform(dayExercisesDao.findByUserId(userId), new Function<DayExercises, Event>() {

            @Override
            public Event apply(DayExercises d) {
                Event e = new Event();
                e.setAllDay(Boolean.TRUE);
                e.setId(d.getId());
                e.setStart(DateFormatUtils.format(d.getDate(), CALENDAR_FORMAT_DATE));
                e.setTitle(d.getExercise().getName());
                
                return e;
            } 
        }));
    }

    @Override
    public Calendars getCalendar(String name) {
        return calendarsDao.findByUser(null, name);
    }

    @Override
    public Event create(Event event, Long userId) throws Exception {
        DayExercises d = new DayExercises();
        d.setDate(DateUtils.parseDate(event.getStart(), new String[] {CALENDAR_FORMAT_DATE}));
        d.setExercise(new Exercises(event.getId()));
        d.setCalendar(calendarsDao.findByUser(userId, null));
        d.setPosition(dayExercisesDao.countByUserIdAndDate(userId, d.getDate()).intValue() + 1);
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
    public void setCalendarsDao(CalendarsDao calendarsDao) {
        this.calendarsDao = calendarsDao;
    }

    @Autowired
    public void setDayExercisesDao(DayExercisesDao dayExercisesDao) {
        this.dayExercisesDao = dayExercisesDao;
    }
}
