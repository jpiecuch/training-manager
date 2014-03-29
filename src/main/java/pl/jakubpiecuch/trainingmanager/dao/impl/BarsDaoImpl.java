package pl.jakubpiecuch.trainingmanager.dao.impl;

import java.util.List;
import pl.jakubpiecuch.trainingmanager.dao.BarsDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Bars;

public class BarsDaoImpl extends CoreDaoImpl implements BarsDao {

    @Override
    public List<Bars> findAll() {
        return session().createQuery("SELECT b FROM Bars b LEFT JOIN FETCH b.strengthUnit LEFT JOIN FETCH b.lengthOfUnit").list();
    }
}
