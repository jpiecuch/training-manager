package pl.jakubpiecuch.trainingmanager.dao.impl;

import java.util.List;
import pl.jakubpiecuch.trainingmanager.dao.PressDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Press;

public class PressDaoImpl extends CoreDaoImpl implements PressDao {

    @Override
    public List<Press> findAll() {
        return session().createQuery("SELECT p FROM Press p LEFT JOIN FETCH p.strengthUnit").list();
    }
}
