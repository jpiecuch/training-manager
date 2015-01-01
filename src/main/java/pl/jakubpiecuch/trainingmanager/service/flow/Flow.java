package pl.jakubpiecuch.trainingmanager.service.flow;

/**
 * Created by Rico on 2014-12-31.
 */
public abstract class Flow {
    public enum Hierarchy {
        PLAN, PHASE, WORKOUT, EXERCISE;

        public Hierarchy getChild() {
            int childId = this.ordinal() + 1;
            return childId < Hierarchy.values().length ? Hierarchy.values()[childId] : null;
        }
    }
    public abstract Hierarchy getHierarchy();
}
