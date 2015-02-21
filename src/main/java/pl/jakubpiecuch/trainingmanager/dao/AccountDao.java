package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.Account;

public interface AccountDao extends CoreDao<Account> {
    Account findByUniques(Long id, String name, String email);
}
