package pl.jakubpiecuch.trainingmanager.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import pl.jakubpiecuch.trainingmanager.dao.hibernate.HibernateDaoSupport;
import pl.jakubpiecuch.trainingmanager.dao.DumbbellsDao;
import pl.jakubpiecuch.trainingmanager.domain.Dumbbells;

public class DumbbellsDaoImpl extends HibernateDaoSupport implements DumbbellsDao {

    @Override
    public List<Dumbbells> findByNotInList(List<Dumbbells> list) {
            return session().createQuery("SELECT d FROM Dumbbells d LEFT JOIN FETCH d.diameterUnit LEFT JOIN FETCH d.lengthOfUnit LEFT JOIN FETCH d.weightUnit  WHERE d NOT IN (:list)").setParameterList("list", list).list();
    }

    @Override
    public List<Dumbbells> findAll() {
        return session().createQuery("SELECT d FROM Dumbbells d LEFT JOIN FETCH d.diameterUnit LEFT JOIN FETCH d.lengthOfUnit LEFT JOIN FETCH d.weightUnit").list();
    }

}
