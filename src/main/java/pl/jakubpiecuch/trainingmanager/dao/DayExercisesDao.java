package pl.jakubpiecuch.trainingmanager.dao;

import java.util.Date;
import java.util.List;
import pl.jakubpiecuch.trainingmanager.domain.DayExercises;

public interface DayExercisesDao {
    DayExercises findById(Long id);
    List<DayExercises> findByCalendarId(Long calendarId);
    List<DayExercises> findByUserIdAndDate(Long id, Date date);
    Long countByUserIdAndDate(Long id, Date date);
    List<DayExercises> findByUserIdAndExerciseId(Long userId, Long exerciseId);
    DayExercises findLastDayExercise(Long id, Date date);
    List<DayExercises> findAll();
}
