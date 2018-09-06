package pl.jakubpiecuch.gymhome.service.repository.accountrecord;

import pl.jakubpiecuch.gymhome.domain.AccountRecord;
import pl.jakubpiecuch.gymhome.service.repository.AbstractConversionRepository;

/**
 * Created by Rico on 2015-01-02.
 */
public class  AccountRecordRepository extends AbstractConversionRepository<AccountRecordDto, AccountRecord, AccountRecordCriteria> {
    @Override
    public AccountRecord getEmpty() {
        return new AccountRecord();
    }
}
