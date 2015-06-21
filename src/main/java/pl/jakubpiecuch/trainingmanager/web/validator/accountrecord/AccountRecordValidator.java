package pl.jakubpiecuch.trainingmanager.web.validator.accountrecord;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by Rico on 2015-06-14.
 */
public class AccountRecordValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
