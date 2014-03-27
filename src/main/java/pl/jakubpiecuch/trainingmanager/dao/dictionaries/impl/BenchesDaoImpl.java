package pl.jakubpiecuch.trainingmanager.dao.dictionaries.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import pl.jakubpiecuch.trainingmanager.dao.hibernate.HibernateDaoSupport;
import pl.jakubpiecuch.trainingmanager.dao.dictionaries.BenchesDao;
import pl.jakubpiecuch.trainingmanager.domain.dictionaries.Benches;

public class BenchesDaoImpl extends HibernateDaoSupport implements BenchesDao {

    @Override
    public List<Benches> findByNotInList(List<Benches> list) {
       return session().createQuery("SELECT b FROM Benches b LEFT JOIN FETCH b.heightUnit LEFT JOIN FETCH b.lengthOfUnit WHERE b NOT IN (:list)").setParameterList("list", list).list();
    }

    @Override
    public List<Benches> findAll() {
        return session().createQuery("SELECT b FROM Benches b LEFT JOIN FETCH b.heightUnit LEFT JOIN FETCH b.lengthOfUnit").list();
    }
}
