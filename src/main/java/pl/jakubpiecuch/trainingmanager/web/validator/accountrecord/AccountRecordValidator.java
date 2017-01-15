package pl.jakubpiecuch.trainingmanager.web.validator.accountrecord;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.service.repository.ReadRepository;
import pl.jakubpiecuch.trainingmanager.service.repository.accountrecord.AccountRecordCriteria;
import pl.jakubpiecuch.trainingmanager.service.repository.accountrecord.AccountRecordDto;
import pl.jakubpiecuch.trainingmanager.web.exception.validator.ValidationException;
import pl.jakubpiecuch.trainingmanager.web.validator.RestrictionCode;
import pl.jakubpiecuch.trainingmanager.web.validator.registration.RegistrationValidator;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rico on 2015-06-14.
 */
public class AccountRecordValidator implements Validator {

    private int precision;
    private ReadRepository<AccountRecordDto, AccountRecordCriteria> repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountRecordDto record = (AccountRecordDto) target;
        if (record.getDate() == null) {
            errors.rejectValue("date", RestrictionCode.REQUIRED);
        } else if (record.getDate().compareTo(DateUtils.truncate(record.getDate(), precision)) != 0) {
            errors.rejectValue("date", RestrictionCode.PRECISION, new Object[]{new ParamsMapBuilder().addParam(RestrictionCode.PRECISION, precision).build()}, null);
        } else {
            AccountRecordCriteria  criteria = new AccountRecordCriteria("en")
                    .setAccountIdRestriction(record.getAccountId())
                    .addTypeRestrictions(record.getType())
                    .addDateRangeRestriction(record.getDate(), record.getDate());
            PageResult<AccountRecordDto> page = repository.page(criteria);
            if (!page.getResult().isEmpty() && page.getResult().get(0).getId() != record.getId()) {
                errors.rejectValue("date", RestrictionCode.UNIQUE);
            }
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", RestrictionCode.REQUIRED);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value", RestrictionCode.REQUIRED);

        if (errors.hasErrors()) {
            throw new ValidationException((BeanPropertyBindingResult) errors);
        }
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setRepository(ReadRepository<AccountRecordDto, AccountRecordCriteria> repository) {
        this.repository = repository;
    }

    private class ParamsMapBuilder {

        private Map<String, Object> map = new HashMap<String, Object>();

        ParamsMapBuilder addParam(String key, Object value) {
            map.put(key, value);
            return this;
        }

        Map build() {
            return map;
        }
    }
}
