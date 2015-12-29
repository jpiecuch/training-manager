package pl.jakubpiecuch.trainingmanager.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.jakubpiecuch.trainingmanager.service.repository.RepoObject;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "account_record")
public class AccountRecord extends CommonEntity implements RepoObject {

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        AccountRecord rhs = (AccountRecord) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.type, rhs.type)
                .append(this.value, rhs.value)
                .append(this.date, rhs.date)
                .append(this.account, rhs.account)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(type)
                .append(value)
                .append(date)
                .append(account)
                .toHashCode();
    }

    public enum Type {
        WEIGHT
    }


}
