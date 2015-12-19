package pl.jakubpiecuch.trainingmanager.dao.core.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;

@Transactional
public class CoreDaoImpl<T extends CommonEntity> implements CoreDao<T> {

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
