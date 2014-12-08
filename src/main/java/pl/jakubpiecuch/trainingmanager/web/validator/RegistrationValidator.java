package pl.jakubpiecuch.trainingmanager.web.validator;

import org.apache.commons.lang.StringUtils;
import pl.jakubpiecuch.trainingmanager.service.user.Authentication;
import pl.jakubpiecuch.trainingmanager.service.user.Registration;
import pl.jakubpiecuch.trainingmanager.web.Response;
import pl.jakubpiecuch.trainingmanager.web.Validator;

/**
 * Created by Rico on 2014-12-07.
 */
public class RegistrationValidator implements Validator<Registration> {

    private int minPasswordLength;
    private int maxPasswordLength;
    private String passwordPattern;
    private String emailPattern;
    private String namePattern;
    private int minNameLength;
    private int maxNameLength;
    private Validator<Authentication> authenticationValidator;

    @Override
    public boolean isValid(Registration object, Response response, String parentName) {
        authenticationValidator.isValid(object, response, parentName);
        if (!response.hasErrors()) {
            if (!object.getPassword().matches(passwordPattern)) {
                response.addError(new Response.Error.Builder(Response.Error.Type.VALIDATION).property("password").restriction(ValidationUtils.Restriction.PATTERN).value(passwordPattern).build());
            } else if (!object.getPassword().equals(object.getRepeat())) {
                response.addError(new Response.Error.Builder(Response.Error.Type.VALIDATION).property("password").restriction(ValidationUtils.Restriction.EQUAL).value("repeat").build());
            } else if (minPasswordLength > object.getPassword().length()) {
                response.addError(new Response.Error.Builder(Response.Error.Type.VALIDATION).property("password").restriction(ValidationUtils.Restriction.MIN_LENGTH).value(Integer.toString(minPasswordLength)).build());
            } else if (maxPasswordLength < object.getPassword().length()) {
                response.addError(new Response.Error.Builder(Response.Error.Type.VALIDATION).property("password").restriction(ValidationUtils.Restriction.MAX_LENGTH).value(Integer.toString(maxPasswordLength)).build());
            }
            ValidationUtils.rejectIfEmpty(object.getFirstName(), response, "firstName");
            ValidationUtils.rejectIfEmpty(object.getLastName(), response, "lastName");

            if (StringUtils.isEmpty(object.getEmail())) {
                response.addError(new Response.Error.Builder(Response.Error.Type.VALIDATION).property("email").restriction(ValidationUtils.Restriction.REQUIRED).build());
            } else if (!object.getEmail().matches(emailPattern)) {
                response.addError(new Response.Error.Builder(Response.Error.Type.VALIDATION).property("email").restriction(ValidationUtils.Restriction.PATTERN).value(emailPattern).build());
            }
            if (!object.getUsername().matches(namePattern)) {
                response.addError(new Response.Error.Builder(Response.Error.Type.VALIDATION).property("username").restriction(ValidationUtils.Restriction.PATTERN).value(namePattern).build());
            } else if (minNameLength > object.getUsername().length()) {
                response.addError(new Response.Error.Builder(Response.Error.Type.VALIDATION).property("username").restriction(ValidationUtils.Restriction.MIN_LENGTH).value(Integer.toString(minNameLength)).build());
            } else if (maxNameLength < object.getUsername().length()) {
                response.addError(new Response.Error.Builder(Response.Error.Type.VALIDATION).property("username").restriction(ValidationUtils.Restriction.MAX_LENGTH).value(Integer.toString(maxNameLength)).build());
            }
        }
        return !response.hasErrors();
    }

    public void setAuthenticationValidator(Validator<Authentication> authenticationValidator) {
        this.authenticationValidator = authenticationValidator;
    }

    public void setMinPasswordLength(int minPasswordLength) {
        this.minPasswordLength = minPasswordLength;
    }

    public void setMaxPasswordLength(int maxPasswordLength) {
        this.maxPasswordLength = maxPasswordLength;
    }

    public void setPasswordPattern(String passwordPattern) {
        this.passwordPattern = passwordPattern;
    }

    public void setEmailPattern(String emailPattern) {
        this.emailPattern = emailPattern;
    }

    public void setNamePattern(String namePattern) {
        this.namePattern = namePattern;
    }

    public void setMinNameLength(int minNameLength) {
        this.minNameLength = minNameLength;
    }

    public void setMaxNameLength(int maxNameLength) {
        this.maxNameLength = maxNameLength;
    }
}
