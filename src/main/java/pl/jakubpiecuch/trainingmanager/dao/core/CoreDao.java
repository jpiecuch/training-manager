package pl.jakubpiecuch.trainingmanager.dao.core;

public interface CoreDao {
    void save(Object o);
    void delete(Object o);
    void flush();
}
