package pl.jakubpiecuch.trainingmanager.service.repository.accountrecord;

import pl.jakubpiecuch.trainingmanager.domain.AccountRecord;
import pl.jakubpiecuch.trainingmanager.service.repository.RepoObject;

import java.util.Date;

/**
 * Created by Rico on 2015-06-14.
 */
public class AccountRecordDto implements RepoObject {

    private Long id;
    private AccountRecord.Type type;
    private String value;
    private Date date;
    private Long accountId;


    @Override
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccountRecord.Type getType() {
        return type;
    }

    public void setType(AccountRecord.Type type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }
}
