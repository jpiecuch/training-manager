package pl.jakubpiecuch.trainingmanager.dao.impl;

import pl.jakubpiecuch.trainingmanager.dao.UsersDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Users;

public class UsersDaoImpl extends CoreDaoImpl implements UsersDao {

    @Override
    public Users findById(Long id) {
        return (Users) session().get(Users.class, id);
    }
    
    @Override
    public Users findByName(String name) {
        return (Users) session().createQuery("SELECT u FROM Users u LEFT JOIN FETCH u.calendar WHERE u.name = :name").setString("name", name).uniqueResult();
    }

    @Override
    public Users findByEmail(String email) {
        return (Users) session().createQuery("SELECT u FROM Users u WHERE u.email = :email").setString("email", email).uniqueResult();
    }
    
}
