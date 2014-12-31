package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.Phase;
import pl.jakubpiecuch.trainingmanager.domain.Plan;

import java.util.List;

public interface PhaseDao extends CoreDao {
    List<Phase> findPhaseByPlanId(long planId);
}
