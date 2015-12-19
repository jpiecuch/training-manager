package pl.jakubpiecuch.trainingmanager.service.repository.accountrecord;

import org.apache.commons.lang.ArrayUtils;
import pl.jakubpiecuch.trainingmanager.domain.AccountRecord;
import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;
import pl.jakubpiecuch.trainingmanager.web.util.AuthenticatedUserUtil;

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

    @Override
    protected String[] getValidFields() {
        return PROPERTIES;
    }

    @Override
    protected void appendRestrictions() {
        restrictions.add(" " + alias + ".account.id = :accountId ");
        params.put("accountId", AuthenticatedUserUtil.getUser().getId());
        if (this.id == null) {
            if (this.from != null) {
                restrictions.add(" " + alias + ".date >= :from ");
                params.put("from", this.from);
            }
            if (this.to != null) {
                restrictions.add(" " + alias + ".date <= :to ");
                params.put("to", this.to);
            }
            collection(this.types, "type", "IN");
        }
    }
}
