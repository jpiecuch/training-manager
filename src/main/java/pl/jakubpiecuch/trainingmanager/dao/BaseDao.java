package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;

public interface BaseDao<T extends CommonEntity> extends CoreDao {
    T findById(long id);
}
