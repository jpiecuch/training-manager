package pl.jakubpiecuch.trainingmanager.web.validator;

import pl.jakubpiecuch.trainingmanager.service.user.Authentication;
import pl.jakubpiecuch.trainingmanager.web.Response;
import pl.jakubpiecuch.trainingmanager.web.Validator;

import java.util.Map;

/**
 * Created by Rico on 2014-11-29.
 */
public class ResponseValidator implements Validator<Object> {

    private Map<Class, Validator> validators;

    @Override
    public boolean isValid(Object object, Response response, String parentName) {
        if (object != null) {
            validators.get(object.getClass()).isValid(object, response, parentName);
        }
        return !response.hasErrors();
    }

    public void setValidators(Map<Class, Validator> validators) {
        this.validators = validators;
    }
}
