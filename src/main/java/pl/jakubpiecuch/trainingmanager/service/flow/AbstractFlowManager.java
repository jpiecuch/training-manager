package pl.jakubpiecuch.trainingmanager.service.flow;

import pl.jakubpiecuch.trainingmanager.dao.BaseDao;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.service.flow.plan.PlanFlow;

/**
 * Created by Rico on 2014-12-31.
 */
public abstract class AbstractFlowManager<T extends FlowObject> implements FlowManager<T>{

    private FlowObjectConverter converter;
    private BaseDao dao;

    @Override
    public T getElement(long id) {
        return (T) converter.toFlowObject(dao.findById(id));
    }

    public void setConverter(FlowObjectConverter<PlanFlow, Plan> converter) {
        this.converter = converter;
    }

    public void setDao(BaseDao dao) {
        this.dao = dao;
    }
}
