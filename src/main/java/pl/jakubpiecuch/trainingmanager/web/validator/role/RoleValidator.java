package pl.jakubpiecuch.trainingmanager.web.validator.role;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.domain.Permissions;
import pl.jakubpiecuch.trainingmanager.domain.Role;
import pl.jakubpiecuch.trainingmanager.service.repository.ReadRepository;
import pl.jakubpiecuch.trainingmanager.service.repository.role.RoleCriteria;
import pl.jakubpiecuch.trainingmanager.web.exception.validator.ValidationException;
import pl.jakubpiecuch.trainingmanager.web.validator.RestrictionCode;

/**
 * Created by Rico on 2015-03-28.
 */
public class RoleValidator implements Validator {

    private ReadRepository<Role, RoleCriteria> readRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Role object = (Role) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, Role.NAME_FIELD_NAME, RestrictionCode.REQUIRED);
        if (ArrayUtils.isNotEmpty(object.getGrantedPermissions())) {
            for (int i = 0; i < object.getGrantedPermissions().length; i++) {
                if (!ArrayUtils.contains(Permissions.getAllPermissions(), object.getGrantedPermissions()[i])) {
                    errors.rejectValue(Role.GRANTED_PERMISSIONS_FIELD_NAME + "[" + i + "]", RestrictionCode.INVALID);
                }
            }
        }
        RoleCriteria criteria = new RoleCriteria().addNameRestrictions(object.getName());
        if (object.getId() != null) {
            criteria.addExcludedIdRestriction(object.getId());
        }
        if (!readRepository.page(criteria).getResult().isEmpty()) {
            errors.rejectValue(Role.NAME_FIELD_NAME, RestrictionCode.UNIQUE);
        }
        if (errors.hasErrors()) {
            throw new ValidationException((BeanPropertyBindingResult) errors);
        }
    }

    public void setReadRepository(ReadRepository<Role, RoleCriteria> readRepository) {
        this.readRepository = readRepository;
    }
}
