package pl.jakubpiecuch.trainingmanager.dao.impl;

import java.util.List;
import pl.jakubpiecuch.trainingmanager.dao.hibernate.HibernateDaoSupport;
import pl.jakubpiecuch.trainingmanager.dao.BarsDao;
import pl.jakubpiecuch.trainingmanager.domain.Bars;

public class BarsDaoImpl extends HibernateDaoSupport implements BarsDao {

    @Override
    public List<Bars> findByNotInList(List<Bars> list) {
            return session().createQuery("SELECT b FROM Bars b LEFT JOIN FETCH b.strengthUnit LEFT JOIN FETCH b.lengthOfUnit WHERE b NOT IN (:list)").setParameterList("list", list).list();
    }

    @Override
    public List<Bars> findAll() {
        return session().createQuery("SELECT b FROM Bars b LEFT JOIN FETCH b.strengthUnit LEFT JOIN FETCH b.lengthOfUnit").list();
    }
}