package pl.jakubpiecuch.trainingmanager.dao.impl;

import pl.jakubpiecuch.trainingmanager.dao.UsersDao;
import pl.jakubpiecuch.trainingmanager.dao.hibernate.HibernateDaoSupport;
import pl.jakubpiecuch.trainingmanager.domain.Users;

public class UsersDaoImpl extends HibernateDaoSupport implements UsersDao {

    @Override
    public Users findById(Long id) {
        return (Users) session().get(Users.class, id);
    }
    
    @Override
    public Users findByName(String name) {
        return (Users) session().createQuery("SELECT u FROM Users u WHERE u.name = :name").setString("name", name).uniqueResult();
    }
    
}