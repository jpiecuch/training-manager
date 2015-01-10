package pl.jakubpiecuch.trainingmanager.service.repository.description;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.dao.DescriptionDao;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.service.repository.Repository;

/**
 * Created by Rico on 2015-01-02.
 */
public class DescriptionRepository implements Repository<Description, DescriptionCriteria> {

    private DescriptionDao descriptionDao;
    private Validator validator;

    @Override
    public PageResult<Description> retrieve(DescriptionCriteria criteria) {
        return descriptionDao.findByCriteria(criteria);
    }

    @Override
    public long create(Description element) {
        validator.validate(element, new BeanPropertyBindingResult(element, "description"));
        descriptionDao.save(element);
        return element.getId();
    }

    @Override
    public void update(Description element) {
        validator.validate(element, new BeanPropertyBindingResult(element, "description"));
        descriptionDao.save(element);
    }

    @Override
    public void delete(long id) {
        descriptionDao.delete(new Description(id));
    }

    public void setDescriptionDao(DescriptionDao descriptionDao) {
        this.descriptionDao = descriptionDao;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }
}
