package pl.jakubpiecuch.trainingmanager.web.validator.account;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by jakub on 29.11.2015.
 */
public class AccountValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
