package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.domain.AccountRecord;
import pl.jakubpiecuch.trainingmanager.service.repository.accountrecord.AccountRecordCriteria;

public interface AccountRecordDao extends RepoDao<AccountRecord, AccountRecordCriteria> {
}
