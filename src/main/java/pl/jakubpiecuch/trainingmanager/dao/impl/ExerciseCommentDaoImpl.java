package pl.jakubpiecuch.trainingmanager.dao.impl;

import pl.jakubpiecuch.trainingmanager.dao.ExerciseCommentDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.ExerciseComment;


public class ExerciseCommentDaoImpl extends CoreDaoImpl<ExerciseComment> implements ExerciseCommentDao {

    @Override
    public ExerciseComment findById(Long id) {
        return (ExerciseComment) session().createQuery("SELECT e FROM ExerciseComment e LEFT JOIN FETCH e.user WHERE e.id = :id").setParameter("id", id).uniqueResult();
    }
}
