package pl.jakubpiecuch.trainingmanager.service.flow;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.dao.BaseDao;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.converter.Converter;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.PlanDto;

import java.util.List;

/**
 * Created by Rico on 2014-12-31.
 */
public abstract class AbstractFlowManager<T extends Flow> implements FlowManager<T> {

    protected Converter converter;
    protected BaseDao dao;
    private Validator validator;

    @Override
    @Transactional
    public T retrieve(long id, boolean full) {
        return (T) converter.fromEntity(dao.findById(id), full);
    }

    @Override
    public List<T> children(long parentId, boolean full) {
        return converter.fromEntityList(dao.findByParentId(parentId), full);
    }

    @Override
    public long save(T element) {
        if (validator != null) {
            validator.validate(element, new BeanPropertyBindingResult(element, element.getHierarchy().name().toLowerCase()));
        }
        CommonEntity entity = converter.toEntity(element);
        dao.save(entity);
        return entity.getId();
    }

    @Override
    public void delete(T element) {
        CommonEntity entity = converter.toEntity(element);
        dao.delete(entity);
    }

    public void setConverter(Converter<PlanDto, Plan> converter) {
        this.converter = converter;
    }

    public void setDao(BaseDao dao) {
        this.dao = dao;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }
}
