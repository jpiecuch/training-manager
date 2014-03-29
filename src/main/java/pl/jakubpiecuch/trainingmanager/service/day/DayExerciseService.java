package pl.jakubpiecuch.trainingmanager.service.day;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jakubpiecuch.trainingmanager.dao.DayExercisesDao;
import pl.jakubpiecuch.trainingmanager.domain.DayExercises;

@Service
public class DayExerciseService implements DayService {

    private DayExercisesDao dayExercisesDao;

    @Override
    public List<DayExercises> getDay(Long id, Date date) {
        return dayExercisesDao.findByUserIdAndDate(id, date);
    }

    @Override
    public List<DayExercises> getProgress(Long userId, Long exerciseId) {
        return dayExercisesDao.findByUserIdAndExerciseId(userId, exerciseId);
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
