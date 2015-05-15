package pl.jakubpiecuch.trainingmanager.dao.impl;

import pl.jakubpiecuch.trainingmanager.dao.PhaseDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Phase;

import java.util.List;

public class PhaseDaoImpl extends CoreDaoImpl<Phase> implements PhaseDao {

    @Override
    public List<Phase> findAll() {
        return session().createQuery("SELECT p FROM Phase p").list();
    }
}