package pl.jakubpiecuch.trainingmanager.service.repository.description;

import pl.jakubpiecuch.trainingmanager.dao.DescriptionDao;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.service.repository.Repository;

/**
 * Created by Rico on 2015-01-02.
 */
public class DescriptionRepository implements Repository<Description, DescriptionCriteria> {

    private DescriptionDao descriptionDao;

    @Override
    public PageResult<Description> retrieve(DescriptionCriteria criteria) {
        return descriptionDao.findByCriteria(criteria);
    }

    public void setDescriptionDao(DescriptionDao descriptionDao) {
        this.descriptionDao = descriptionDao;
    }
}
