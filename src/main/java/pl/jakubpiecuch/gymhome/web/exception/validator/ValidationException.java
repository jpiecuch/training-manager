package pl.jakubpiecuch.gymhome.web.exception.validator;

import org.springframework.validation.BeanPropertyBindingResult;

/**
 * Created by Rico on 2014-12-21.
 */
public class ValidationException extends RuntimeException {
    private final BeanPropertyBindingResult errors;

    public ValidationException(BeanPropertyBindingResult errors) {
        this.errors = errors;
    }

    public BeanPropertyBindingResult getErrors() {
        return errors;
    }
}
