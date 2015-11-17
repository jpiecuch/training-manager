package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import com.google.common.collect.Lists;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.PlanDao;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.repository.Repository;
import pl.jakubpiecuch.trainingmanager.web.exception.notfound.NotFoundException;

import java.util.List;

/**
 * Created by Rico on 2014-12-31.
 */
public class PlanRepository implements Repository<PlanDto, PlanCriteria> {

    private PlanConverter converter;
    private PlanDao dao;
    private Validator validator;

    @Override
    @Transactional
    public PageResult<PlanDto> read(PlanCriteria criteria) {
        RepoDao repoDao = dao;
        PageResult<Plan> result = repoDao.findByCriteria(criteria);
        final List<PlanDto> list = Lists.newArrayList(converter.fromEntities(result.getResult()));
        final long count = result.getCount();
        return new PageResult<PlanDto>() {
            @Override
            public List<PlanDto> getResult() {
                return list;
            }

            @Override
            public long getCount() {
                return count;
            }
        };
    }

    @Override
    @Transactional
    public PlanDto retrieve(long id) {
        Plan flow = dao.findById(id);
        if (flow == null) {
            throw new NotFoundException();
        }
        return converter.fromEntity(flow);
    }

    @Override
    public long create(PlanDto element) {
        if (validator != null) {
            validator.validate(element, new BeanPropertyBindingResult(element, "plan"));
        }
        Plan entity = converter.toEntity(element, new Plan());
        dao.create(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public void update(PlanDto element) {
        if (validator != null) {
            validator.validate(element, new BeanPropertyBindingResult(element, "plan"));
        }
        Plan entity = converter.toEntity(element, dao.findById(element.getId()));
        dao.update(entity);
    }

    @Override
    public void delete(long id) {
        Plan entity = dao.findById(id);
        dao.delete(entity);
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setConverter(PlanConverter converter) {
        this.converter = converter;
    }

    public void setDao(PlanDao dao) {
        this.dao = dao;
    }
}
