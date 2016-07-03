package pl.jakubpiecuch.trainingmanager.dao.core.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;

@Transactional
public class CoreDaoImpl<T extends CommonEntity> implements CoreDao<T> {

    public static final String NOT_NULL_OBJECT_REQUIRED = "Object cannot be null";
    private SessionFactory sessionFactory;
    private Class clazz;

    protected Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void flush() {
        session().flush();
    }

    @Override
    public Long create(T o) {
        Assert.notNull(o, NOT_NULL_OBJECT_REQUIRED);
        return (Long) session().save(o);
    }

    @Override
    public void update(T o) {
        session().update(o);
    }

    @Override
    public void delete(T o) {
        session().delete(o);
    }

    @Override
    public T findById(Long id) {
        return (T) session().get(clazz, id);
    }

    @Required
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Required
    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
