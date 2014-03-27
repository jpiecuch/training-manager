package pl.jakubpiecuch.trainingmanager.dao.dictionaries.impl;

import java.util.List;
import pl.jakubpiecuch.trainingmanager.dao.hibernate.HibernateDaoSupport;
import pl.jakubpiecuch.trainingmanager.dao.dictionaries.StandsDao;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Stands;

public class StandsDaoImpl extends HibernateDaoSupport implements StandsDao {

    @Override
    public List<Stands> findByNotInList(List<Stands> list) {
            return session().createQuery("SELECT s FROM Stands s LEFT JOIN FETCH s.heightMaxUnit LEFT JOIN FETCH s.heightMinUnit WHERE s NOT IN (:list)").setParameterList("list", list).list();
    }

    @Override
    public List<Stands> findAll() {
        return session().createQuery("SELECT s FROM Stands s LEFT JOIN FETCH s.heightMaxUnit LEFT JOIN FETCH s.heightMinUnit").list();
    }
}
