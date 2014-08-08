package pl.jakubpiecuch.trainingmanager.service.day;

import java.util.Date;
import java.util.List;
import pl.jakubpiecuch.trainingmanager.domain.DayExercises;
import pl.jakubpiecuch.trainingmanager.domain.Users;

public interface DayService {
    List<DayExercises> day(Users user, Date date);
    List<DayExercises> exerciseProgress(Users user, Long exerciseId);
    void save(DayExercises dayExercises);
    DayExercises exercise(Long id);
}
