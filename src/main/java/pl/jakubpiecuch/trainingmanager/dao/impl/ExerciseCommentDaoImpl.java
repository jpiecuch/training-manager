package pl.jakubpiecuch.trainingmanager.dao.impl;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import pl.jakubpiecuch.trainingmanager.dao.ExerciseCommentDao;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Exercise;
import pl.jakubpiecuch.trainingmanager.domain.ExerciseComment;

import java.util.List;

public class ExerciseCommentDaoImpl extends CoreDaoImpl implements ExerciseCommentDao {

    @Override
    public PageResult<ExerciseComment> getPage(long commentedId, int first, int max) {
        final List<Object[]> result = session().createQuery("SELECT c, over(count(*)) FROM ExerciseComment c LEFT JOIN FETCH c.user u WHERE c.commented.id = :id").setParameter("id", commentedId).setFirstResult(first).setMaxResults(max).list();

        return new PageResult<ExerciseComment>() {
            @Override
            public List<ExerciseComment> getResult() {
                return Lists.transform(result, new Function<Object[], ExerciseComment>() {
                    @Override
                    public ExerciseComment apply(Object[] in) {
                        return (ExerciseComment) in[0];
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
    public ExerciseComment findById(Long id) {
        return (ExerciseComment) session().createQuery("SELECT e FROM ExerciseComment e LEFT JOIN FETCH e.user WHERE e.id = :id").setParameter("id", id).uniqueResult();
    }
}
