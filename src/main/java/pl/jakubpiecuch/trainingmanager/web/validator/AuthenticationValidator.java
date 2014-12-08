package pl.jakubpiecuch.trainingmanager.web.validator;

import org.springframework.stereotype.Service;
import pl.jakubpiecuch.trainingmanager.service.user.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.Provider;
import pl.jakubpiecuch.trainingmanager.web.Response;
import pl.jakubpiecuch.trainingmanager.web.Validator;

/**
 * Created by Rico on 2014-11-29.
 */
@Service
public class AuthenticationValidator implements Validator<Authentication> {

    @Override
    public boolean isValid(Authentication object, Response response, String parentName) {
        ValidationUtils.rejectIfEmpty(object.getUsername(), response, "username");
        ValidationUtils.rejectIfEmpty(object.getPassword(), response, "password");
        if (Provider.Type.SOCIAL == object.getProvider() && object.getSocial() == null) {
            response.addError(new Response.Error.Builder(Response.Error.Type.VALIDATION).property("social").restriction(ValidationUtils.Restriction.REQUIRED).build());
        }
        return !response.hasErrors();
    }
}
