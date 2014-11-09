package pl.jakubpiecuch.trainingmanager.service.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.jakubpiecuch.trainingmanager.dao.PlanDao;
import pl.jakubpiecuch.trainingmanager.domain.Plan;

@Service
public class DbPlanService implements PlanService {

    private PlanDao planDao;

    @Override
    public Plan getPlan(long id) {
        return planDao.getById(id);
    }

    @Autowired
    public void setPlanDao(PlanDao planDao) {
        this.planDao = planDao;
    }
}
