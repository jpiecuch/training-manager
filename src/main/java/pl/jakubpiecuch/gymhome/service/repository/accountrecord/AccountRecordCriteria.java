package pl.jakubpiecuch.gymhome.service.repository.accountrecord;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import pl.jakubpiecuch.gymhome.dao.impl.Criteria;
import pl.jakubpiecuch.gymhome.domain.AccountRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Rico on 2015-06-13.
 */
public class AccountRecordCriteria extends Criteria<AccountRecordCriteria> {
    private static final String[] PROPERTIES = new String[]{"id", "type", "date", "account"};

    private Date from;
    private Date to;
    private List<AccountRecord.Type> types = new ArrayList<AccountRecord.Type>();
    private Long accountId;

    public AccountRecordCriteria(String lang) {
        super("a", "AccountRecord", lang);
    }

    public AccountRecordCriteria addDateRangeRestriction(Date from, Date to) {
        this.from = from;
        this.to = to;
        return this;
    }

    public AccountRecordCriteria addTypeRestrictions(AccountRecord.Type... type) {
        if (ArrayUtils.isNotEmpty(type)) {
            this.types.addAll(Arrays.asList(type));
        }
        return this;
    }

    public AccountRecordCriteria setAccountIdRestriction(Long accountId) {
        this.accountId = accountId;
        return this;
    }

    @Override
    protected String[] getValidFields() {
        return PROPERTIES;
    }

    @Override
    protected void appendRestrictions() {
        appendUserRestriction("account.id", accountId);
        appendFromToRestrictions("date", this.from, this.to);
        if (this.id == null) {
            collection(this.types, "type", "IN");
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
        AccountRecordCriteria rhs = (AccountRecordCriteria) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.from, rhs.from)
                .append(this.to, rhs.to)
                .append(this.types, rhs.types)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(from)
                .append(to)
                .append(types)
                .toHashCode();
    }
}
