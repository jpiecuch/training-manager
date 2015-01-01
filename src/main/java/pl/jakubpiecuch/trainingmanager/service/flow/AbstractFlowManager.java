package pl.jakubpiecuch.trainingmanager.service.flow;

import org.springframework.transaction.annotation.Transactional;
import pl.jakubpiecuch.trainingmanager.dao.BaseDao;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.PlanDto;

import java.util.List;

/**
 * Created by Rico on 2014-12-31.
 */
public abstract class AbstractFlowManager<T extends Flow> implements FlowManager<T> {

    private FlowConverter converter;
    private BaseDao dao;

    @Override
    @Transactional
    public T retrieve(long id) {
        return (T) converter.toFlowObject(dao.findById(id));
    }

    @Override
    public List<T> children(long parentId) {
        return converter.toFlowObjectList(dao.findByParentId(parentId));
    }

    @Override
    public long create(T element) {
        CommonEntity entity = converter.fromFlowObject(element);
        dao.save(entity);
        return entity.getId();
    }

    public void setConverter(FlowConverter<PlanDto, Plan> converter) {
        this.converter = converter;
    }

    public void setDao(BaseDao dao) {
        this.dao = dao;
    }
}
