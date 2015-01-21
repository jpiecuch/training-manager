package pl.jakubpiecuch.trainingmanager.dao.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import pl.jakubpiecuch.trainingmanager.dao.DescriptionDao;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.service.repository.description.DescriptionCriteria;

import java.util.List;

public class DescriptionDaoImpl extends CoreDaoImpl  implements DescriptionDao {

    @Override
    public Long count() {
        return (Long) session().createQuery("SELECT count(*) FROM Description").uniqueResult();
    }

    @Override
    public PageResult<Description> findByCriteria(DescriptionCriteria criteria) {
        final List<Object[]> result = criteria.query(session()).list();

        return new PageResult<Description>() {
            @Override
            public List<Description> getResult() {
                return Lists.transform(result, new Function<Object[], Description>() {
                    @Override
                    public Description apply(Object[] in) {
                        return (Description) in[0];
                    }
                });
            }

            @Override
            public long getCount() {
                return result.size() > 0 ? (Long)result.get(0)[1] : 0;
            }
        };
    }

    @Override
    public Description findById(Long id) {
        return (Description) session().get(Description.class, id);
    }

}
