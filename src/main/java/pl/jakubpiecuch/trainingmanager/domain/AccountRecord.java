package pl.jakubpiecuch.trainingmanager.domain;

import pl.jakubpiecuch.trainingmanager.service.repository.RepoObject;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "account_record")
public class AccountRecord extends CommonEntity implements RepoObject {

    public enum Type {
        WEIGHT
    }

    private Type type;
    private String value;
    private Date date;
    private Account account;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
