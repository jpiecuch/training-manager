package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.Workout;

import java.util.List;

public interface WorkoutDao extends CoreDao {
    List<Workout> findByPhaseId(long phaseId);
}
