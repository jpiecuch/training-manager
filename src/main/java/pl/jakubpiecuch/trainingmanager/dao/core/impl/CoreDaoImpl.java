package pl.jakubpiecuch.trainingmanager.dao.core.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;

@Transactional
public class CoreDaoImpl implements CoreDao {

    private SessionFactory sessionFactory;

    protected Session session() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(Object o) {
        session().saveOrUpdate(o);
    }

    @Override
    public void delete(Object o) {
        session().delete(o);
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    } 
}
