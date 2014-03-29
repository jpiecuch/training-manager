package pl.jakubpiecuch.trainingmanager.dao.impl;

import java.util.List;
import pl.jakubpiecuch.trainingmanager.dao.LoadsDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Loads;

public class LoadsDaoImpl extends CoreDaoImpl  implements LoadsDao {

    @Override
    public List<Loads> findByNotInList(List<Loads> list) {
            return session().createQuery("SELECT l FROM Loads l LEFT JOIN FETCH l.holeDiameterUnit LEFT JOIN FETCH l.weightUnit WHERE l NOT IN (:list) ORDER BY l.weight").setParameterList("list", list).list();
    }

    @Override
    public List<Loads> findAll() {
        return session().createQuery("SELECT l FROM Loads l LEFT JOIN FETCH l.holeDiameterUnit LEFT JOIN FETCH l.weightUnit ORDER BY l.weight").list();
    }
}
