package pl.jakubpiecuch.trainingmanager.dao.core.impl;

import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.dao.hibernate.HibernateDaoSupport;

public class CoreDaoImpl extends HibernateDaoSupport implements CoreDao {

    @Override
    public void save(Object o) {
        session().saveOrUpdate(o);
    }

    @Override
    public void delete(Object o) {
        session().delete(o);
    }
}
