package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.Execution;

import java.util.List;

public interface ExecutionDao extends CoreDao<Execution> {
    List<Execution> findByParentId(long parentId);
}
