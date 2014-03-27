package pl.jakubpiecuch.trainingmanager.service.calendar;

import com.google.common.collect.Sets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.dao.plans.CalendarsDao;
import pl.jakubpiecuch.trainingmanager.dao.plans.DayExercisesDao;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Exercises;
import pl.jakubpiecuch.trainingmanager.domain.plans.Calendars;
import pl.jakubpiecuch.trainingmanager.domain.plans.DayExercises;

@Service
public class FullCallendarService implements CalendarService {
    private final static String CALENDAR_FORMAT_DATE = "yyyy-MM-dd";

    private DayExercisesDao dayExercisesDao;
    private CalendarsDao calendarsDao;
    private CoreDao coreDao;

    @Override
    public  List<Event> getEvents(Long userId) {
        List<DayExercises> dayExerciseList = dayExercisesDao.findByCalendarId(calendarsDao.findByUser(userId, null).getId());
        if (dayExerciseList != null) {
            List<Event> result = new ArrayList<Event>();
            for (final DayExercises d : dayExerciseList) {
                Event c = new Event();
                c.setAllDay(Boolean.TRUE);
                c.setId(d.getId());
                c.setStart(DateFormatUtils.format(d.getDate(), CALENDAR_FORMAT_DATE));
                c.setTitle(d.getExercise().getName());
                result.add(c);
            }
            return result;
        }
        return null;
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
        coreDao.save(d);
        event.setId(d.getId());
        event.setAllDay(Boolean.TRUE);
        return event;
    }

    @Override
    public void move(Event event) throws Exception {
        DayExercises dayExercise = dayExercisesDao.findById(event.getId());
        dayExercise.setDate(new SimpleDateFormat(CALENDAR_FORMAT_DATE).parse(event.getStart()));
        coreDao.save(dayExercise);

    }

    @Override
    public void remove(Event event) {
        DayExercises dayExercise = new DayExercises();
        dayExercise.setId(event.getId());
        coreDao.delete(dayExercise);
    }

    @Autowired
    public void setCalendarsDao(CalendarsDao calendarsDao) {
        this.calendarsDao = calendarsDao;
    }

    @Autowired
    public void setCoreDao(CoreDao coreDao) {
        this.coreDao = coreDao;
    }

    @Autowired
    public void setDayExercisesDao(DayExercisesDao dayExercisesDao) {
        this.dayExercisesDao = dayExercisesDao;
    }
}
