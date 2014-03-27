package pl.jakubpiecuch.trainingmanager.dao.impl;

import java.util.List;
import pl.jakubpiecuch.trainingmanager.dao.hibernate.HibernateDaoSupport;
import pl.jakubpiecuch.trainingmanager.dao.NecksDao;
import pl.jakubpiecuch.trainingmanager.domain.Necks;

public class NecksDaoImpl extends HibernateDaoSupport implements NecksDao {

    @Override
    public List<Necks> findByNotInList(List<Necks> list) {
            return session().createQuery("SELECT n FROM Necks n LEFT JOIN FETCH n.diameterUnit LEFT JOIN FETCH n.lengthOfUnit LEFT JOIN FETCH n.weightUnit WHERE n NOT IN (:list)").setParameterList("list", list).list();
    }

    @Override
    public List<Necks> findAll() {
        return session().createQuery("SELECT n FROM Necks n LEFT JOIN FETCH n.diameterUnit LEFT JOIN FETCH n.lengthOfUnit LEFT JOIN FETCH n.weightUnit").list();
    }
}
