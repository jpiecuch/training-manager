package pl.jakubpiecuch.trainingmanager.service.repository;

/**
 * Created by Rico on 2015-01-02.
 */
public class Criteria<T extends Criteria> {
    protected Long id;
    protected Integer firstResult;
    protected Integer maxResults;

    public T setIdRestriction(Long id) {
        this.id = id;
        return (T) this;
    }

    public T setFirstResultRestriction(Integer firstResult) {
        this.firstResult = firstResult;
        return (T) this;
    }

    public T setMaxResultsRestriction(Integer maxResults) {
        this.maxResults = maxResults;
        return (T) this;
    }
}
