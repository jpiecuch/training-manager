package pl.jakubpiecuch.trainingmanager.web.exception.validator;

import org.springframework.validation.Errors;

/**
 * Created by Rico on 2014-12-21.
 */
public class ValidationException extends RuntimeException {
    private final Errors errors;

    public ValidationException(Errors errors) {
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
