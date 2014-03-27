package pl.jakubpiecuch.trainingmanager.service.day;

import java.util.Date;
import java.util.List;
import pl.jakubpiecuch.trainingmanager.domain.plans.DayExercises;

public interface DayService {
    List<DayExercises> getDay(Long id, Date date);
    List<DayExercises> getProgress(Long userId, Long exerciseId);
    void save(DayExercises dayExercises);
    
}
