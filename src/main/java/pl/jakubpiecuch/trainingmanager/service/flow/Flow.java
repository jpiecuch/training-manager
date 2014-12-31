package pl.jakubpiecuch.trainingmanager.service.flow;

/**
 * Created by Rico on 2014-12-31.
 */
public abstract class Flow {
    public enum Hierarchy {PLAN, PHASE, WORKOUT, EXERCISE}
    public abstract int getFlowPosition();
}
