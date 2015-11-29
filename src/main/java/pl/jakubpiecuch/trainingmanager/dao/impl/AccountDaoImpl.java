package pl.jakubpiecuch.trainingmanager.dao.impl;

import org.apache.commons.lang.StringUtils;
import pl.jakubpiecuch.trainingmanager.dao.AccountDao;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.service.repository.account.AccountCriteria;

public class AccountDaoImpl extends AbstractRepoDao<Account, AccountCriteria> implements AccountDao {

    @Override
    public Account findByUniques(Long id, String name, String email) {
        return id != null ? findById(id) : StringUtils.isNotEmpty(name) ? findByName(name) : StringUtils.isNotEmpty(email) ? findByEmail(email) : null;
    }

    private Account findByName(String name) {
        return (Account) session().createQuery("SELECT u FROM Account u WHERE u.name = :name").setString("name", name).uniqueResult();
    }

    private Account findByEmail(String email) {
        return (Account) session().createQuery("SELECT u FROM Account u WHERE u.email = :email").setString("email", email).uniqueResult();
    }

     
}
