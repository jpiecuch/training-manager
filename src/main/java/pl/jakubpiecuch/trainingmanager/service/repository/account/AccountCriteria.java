package pl.jakubpiecuch.trainingmanager.service.repository.account;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import pl.jakubpiecuch.trainingmanager.domain.Account;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.domain.Role;
import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rico on 2015-03-28.
 */
public class AccountCriteria extends Criteria<AccountCriteria> {

    private static final String[] PROPERTIES = new String[] {CommonEntity.ID_FIELD_NAME, Account.NAME_FIELD_NAME, Account.EMAIL_FIELD_NAME, Account.STATUS_FIELD_NAME};

    private List<String> names = new ArrayList<String>();
    private List<String> emails = new ArrayList<String>();
    private List<Account.Status> statuses = new ArrayList<Account.Status>();

    public AccountCriteria() {
        super("a", "Account", null);
    }

    public AccountCriteria addNameRestrictions(String... name) {
        if (ArrayUtils.isNotEmpty(name)) {
            this.names.addAll(Arrays.asList(name));
        }
        return this;
    }

    public AccountCriteria addEmailRestrictions(String... email) {
        if (ArrayUtils.isNotEmpty(email)) {
            this.emails.addAll(Arrays.asList(email));
        }
        return this;
    }

    public AccountCriteria addStatusRestrictions(Account.Status... status) {
        if (ArrayUtils.isNotEmpty(status)) {
            this.statuses.addAll(Arrays.asList(status));
        }
        return this;
    }

    @Override
    protected String[] getValidFields() {
        return PROPERTIES;
    }

    @Override
    protected void appendRestrictions() {
        if (this.id == null) {
            collection(this.names, Account.NAME_FIELD_NAME, "IN");
            collection(this.emails, Account.EMAIL_FIELD_NAME, "IN");
            collection(this.statuses, Account.STATUS_FIELD_NAME, "IN");
        }
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
        AccountCriteria rhs = (AccountCriteria) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.names, rhs.names)
                .append(this.emails, rhs.emails)
                .append(this.statuses, rhs.statuses)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(names)
                .append(emails)
                .append(statuses)
                .toHashCode();
    }
}
