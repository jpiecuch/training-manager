package pl.jakubpiecuch.trainingmanager.dao.impl;

import java.util.List;
import pl.jakubpiecuch.trainingmanager.dao.PressDao;
import pl.jakubpiecuch.trainingmanager.dao.hibernate.HibernateDaoSupport;
import pl.jakubpiecuch.trainingmanager.domain.Press;

public class PressDaoImpl extends HibernateDaoSupport implements PressDao {

    @Override
    public List<Press> findByNotInList(List<Press> list) {
            return session().createQuery("SELECT p FROM Press p LEFT JOIN FETCH p.strengthUnit WHERE p NOT IN (:list)").setParameterList("list", list).list();
    }

    @Override
    public List<Press> findAll() {
        return session().createQuery("SELECT p FROM Press p LEFT JOIN FETCH p.strengthUnit").list();
    }
}