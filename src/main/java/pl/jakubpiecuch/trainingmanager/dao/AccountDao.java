package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.service.repository.account.AccountCriteria;

public interface AccountDao extends RepoDao<Account, AccountCriteria>  {
}
