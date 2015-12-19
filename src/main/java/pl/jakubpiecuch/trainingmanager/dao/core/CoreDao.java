package pl.jakubpiecuch.trainingmanager.dao.core;

import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;

public interface CoreDao<T extends CommonEntity> {
    Long create(T o);

    void update(T o);

    void delete(T o);

    void flush();

    T findById(Long id);
}
