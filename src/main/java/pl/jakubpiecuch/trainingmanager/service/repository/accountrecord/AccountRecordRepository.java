package pl.jakubpiecuch.trainingmanager.service.repository.accountrecord;

import pl.jakubpiecuch.trainingmanager.domain.AccountRecord;
import pl.jakubpiecuch.trainingmanager.service.repository.AbstractConversionRepository;

/**
 * Created by Rico on 2015-01-02.
 */
public class AccountRecordRepository extends AbstractConversionRepository<AccountRecordDto, AccountRecord, AccountRecordCriteria> {
    @Override
    public AccountRecord getEmpty() {
        return new AccountRecord();
    }
}
