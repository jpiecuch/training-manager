package pl.jakubpiecuch.trainingmanager.service.flow;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.dao.BaseDao;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.PlanDto;

import java.util.List;

/**
 * Created by Rico on 2014-12-31.
 */
public abstract class AbstractFlowManager<T extends Flow> implements FlowManager<T> {

    protected FlowConverter converter;
    protected BaseDao dao;
    private Validator validator;

    @Override
    @Transactional
    public T retrieve(long id, boolean full) {
        return (T) converter.toFlowObject(dao.findById(id), full);
    }

    @Override
    public List<T> children(long parentId, boolean full) {
        return converter.toFlowObjectList(dao.findByParentId(parentId), full);
    }

    @Override
    public long save(T element) throws Exception {
        validator.validate(element, new BeanPropertyBindingResult(element, element.getHierarchy().name().toLowerCase()));
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

    public void setValidator(Validator validator) {
        this.validator = validator;
    }
}
