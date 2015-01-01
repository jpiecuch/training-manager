package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;

import java.util.List;

public interface BaseDao<T extends CommonEntity> extends CoreDao {
    T findById(long id);
    List<T> findByParentId(long parentId);
}
