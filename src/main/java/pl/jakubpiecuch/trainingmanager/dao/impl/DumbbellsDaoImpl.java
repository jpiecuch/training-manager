package pl.jakubpiecuch.trainingmanager.dao.impl;

import java.util.List;
import pl.jakubpiecuch.trainingmanager.dao.DumbbellsDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Dumbbells;

public class DumbbellsDaoImpl extends CoreDaoImpl  implements DumbbellsDao {

    @Override
    public List<Dumbbells> findAll() {
        return session().createQuery("SELECT d FROM Dumbbells d LEFT JOIN FETCH d.diameterUnit LEFT JOIN FETCH d.lengthOfUnit LEFT JOIN FETCH d.weightUnit").list();
    }

}
