package pl.jakubpiecuch.trainingmanager.web.validator.role;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.domain.Permissions;
import pl.jakubpiecuch.trainingmanager.domain.Role;
import pl.jakubpiecuch.trainingmanager.web.exception.validator.ValidationException;
import pl.jakubpiecuch.trainingmanager.web.validator.RestrictionCode;

/**
 * Created by Rico on 2015-03-28.
 */
public class RoleValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Role object = (Role) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, Role.NAME_FIELD_NAME, RestrictionCode.REQUIRED);
        if (ArrayUtils.isNotEmpty(object.getGrantedPermissions())) {
            int i = 0;
            for (String permission : object.getGrantedPermissions()) {
                if (!ArrayUtils.contains(Permissions.ALL, permission)) {
                    errors.rejectValue(Role.GRANTED_PERMISSIONS_FIELD_NAME + "[" + i + "]", RestrictionCode.INVALID);
                }
                i++;
            }
        }
        if (errors.hasErrors()) {
            throw new ValidationException(errors);
        }
    }
}
