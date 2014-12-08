package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.apache.commons.lang.StringUtils;
import pl.jakubpiecuch.trainingmanager.dao.UsersDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.Account;

public class UsersDaoImpl extends CoreDaoImpl implements UsersDao {

    @Override
    public Account findByUniques(Long id, String name, String email) {
        return id != null ? findById(id) : StringUtils.isNotEmpty(name) ? findByName(name) : StringUtils.isNotEmpty(email) ? findByEmail(email) : null;
    }

    private Account findById(Long id) {
        return (Account) session().createQuery("SELECT u FROM Account u WHERE u.id = :id").setParameter("id", id).uniqueResult();
    }

    private Account findByName(String name) {
        return (Account) session().createQuery("SELECT u FROM Account u WHERE u.name = :name").setString("name", name).uniqueResult();
    }

    private Account findByEmail(String email) {
        return (Account) session().createQuery("SELECT u FROM Account u WHERE u.email = :email").setString("email", email).uniqueResult();
    }

     
}
