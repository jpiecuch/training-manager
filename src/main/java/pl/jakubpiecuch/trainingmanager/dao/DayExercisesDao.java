package pl.jakubpiecuch.trainingmanager.dao;

import java.util.Date;
import java.util.List;
import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.DayExercises;

public interface DayExercisesDao extends CoreDao {
    DayExercises findById(Long id);
    List<DayExercises> findByCalendarId(Long calendarId);
    List<DayExercises> findByCalendarIdAndDate(Long id, Date date);
    Long countByUserIdAndDate(Long id, Date date);
    List<DayExercises> findByCalendarIdAndExerciseId(Long userId, Long exerciseId);
    DayExercises findLastDayExercise(Long id, Date date);
}
