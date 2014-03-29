package pl.jakubpiecuch.trainingmanager.dao.impl;

import java.util.List;
import pl.jakubpiecuch.trainingmanager.dao.NecksDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Necks;

public class NecksDaoImpl extends CoreDaoImpl  implements NecksDao {

    @Override
    public List<Necks> findAll() {
        return session().createQuery("SELECT n FROM Necks n LEFT JOIN FETCH n.diameterUnit LEFT JOIN FETCH n.lengthOfUnit LEFT JOIN FETCH n.weightUnit").list();
    }
}
