package pl.jakubpiecuch.trainingmanager.dao;

public interface BaseCommentDao<T> {
    PageResult<T> getPage(long commentedId, int first, int max);
    T findById(Long id);
}
