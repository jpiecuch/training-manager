package pl.jakubpiecuch.trainingmanager.dao.impl;

import java.util.List;
import pl.jakubpiecuch.trainingmanager.dao.BenchesDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Benches;

public class BenchesDaoImpl extends CoreDaoImpl implements BenchesDao {

    @Override
    public List<Benches> findAll() {
        return session().createQuery("SELECT b FROM Benches b LEFT JOIN FETCH b.heightUnit LEFT JOIN FETCH b.lengthOfUnit").list();
    }
}
