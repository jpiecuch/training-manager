package pl.jakubpiecuch.trainingmanager.service.day;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jakubpiecuch.trainingmanager.dao.DayExercisesDao;
import pl.jakubpiecuch.trainingmanager.domain.DayExercises;
import pl.jakubpiecuch.trainingmanager.domain.Users;

@Service
public class DayExerciseService implements DayService {

    private DayExercisesDao dayExercisesDao;

    @Override
    public List<DayExercises> getDay(Users user, Date date) {
        return dayExercisesDao.findByCalendarIdAndDate(user.getCalendar().getId(), date);
    }

    @Override
    public List<DayExercises> getProgress(Users user, Long exerciseId) {
        return dayExercisesDao.findByCalendarIdAndExerciseId(user.getCalendar().getId(), exerciseId);
    }

    @Override
    public void save(DayExercises dayExercises) {
        dayExercisesDao.save(dayExercises);
    }

    @Autowired
    public void setDayExercisesDao(DayExercisesDao dayExercisesDao) {
        this.dayExercisesDao = dayExercisesDao;
    }
}
