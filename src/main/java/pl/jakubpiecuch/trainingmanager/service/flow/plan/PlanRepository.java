package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import pl.jakubpiecuch.trainingmanager.dao.util.DaoAssert;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.repository.AbstractConversionRepository;


/**
 * Created by Rico on 2014-12-31.
 */
public class PlanRepository extends AbstractConversionRepository<PlanDto, Plan, PlanCriteria> {

    private static final String PLAN_USED_CODE = "plan.used.error";

    @Override
    @Transactional
    public PlanDto unique(long id) {
        return super.unique(id);
    }

    @Override
    @Transactional
    public void update(PlanDto element) {
        super.update(element);
    }

    @Override
    public void delete(long id) {
        Plan plan = dao.findById(id);
        DaoAssert.notNull(plan);
        Assert.isTrue(!plan.isUsed(), PLAN_USED_CODE);
        super.delete(plan);
    }

    @Override
    public Plan getEmpty() {
        return new Plan();
    }
}
