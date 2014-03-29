package pl.jakubpiecuch.trainingmanager.dao.impl;

import java.util.List;
import pl.jakubpiecuch.trainingmanager.dao.StandsDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Stands;

public class StandsDaoImpl extends CoreDaoImpl  implements StandsDao {

    @Override
    public List<Stands> findAll() {
        return session().createQuery("SELECT s FROM Stands s LEFT JOIN FETCH s.heightMaxUnit LEFT JOIN FETCH s.heightMinUnit").list();
    }
}
