package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.flow.AbstractFlowManager;
import pl.jakubpiecuch.trainingmanager.service.repository.ReadRepository;

import java.util.List;

/**
 * Created by Rico on 2014-12-31.
 */
public class PlanManager extends AbstractFlowManager<PlanDto> implements ReadRepository<PlanDto, PlanCriteria> {

    @Override
    public PageResult<PlanDto> read(PlanCriteria criteria) {
        RepoDao repoDao = (RepoDao) dao;
        final PageResult<Plan> result = repoDao.findByCriteria(criteria);

        return new PageResult<PlanDto>() {
            @Override
            public List<PlanDto> getResult() {
                return converter.fromEntityList(result.getResult(), false);
            }

            @Override
            public long getCount() {
                return result.getCount();
            }
        };
    }
}
