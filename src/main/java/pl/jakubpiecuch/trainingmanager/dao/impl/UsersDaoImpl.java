package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import pl.jakubpiecuch.trainingmanager.dao.UsersDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Users;

public class UsersDaoImpl extends CoreDaoImpl implements UsersDao {

    @Override
    public Users findByUniques(Long id, String name, String email) {
        return id != null ? findById(id) : StringUtils.isNotEmpty(name) ? findByName(name) : StringUtils.isNotEmpty(email) ? findByEmail(email) : null;
    }

    private Users findById(Long id) {
        return (Users) session().createQuery("SELECT u FROM Users u LEFT JOIN FETCH u.calendar WHERE u.id = :id").setParameter("id", id).uniqueResult();
    }

    private Users findByName(String name) {
        return (Users) session().createQuery("SELECT u FROM Users u LEFT JOIN FETCH u.calendar WHERE u.name = :name").setString("name", name).uniqueResult();
    }

    private Users findByEmail(String email) {
        return (Users) session().createQuery("SELECT u FROM Users u WHERE u.email = :email").setString("email", email).uniqueResult();
    }

     
}
