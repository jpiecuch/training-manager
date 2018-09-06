package pl.jakubpiecuch.gymhome.service.flow.plan;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import pl.jakubpiecuch.gymhome.domain.CommonEntity;
import pl.jakubpiecuch.gymhome.web.validator.RestrictionCode;

/**
 * Created by Rico on 2014-11-29.
 */
public class UpdatePlanValidator extends InsertPlanValidator {

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, CommonEntity.ID_FIELD_NAME, RestrictionCode.REQUIRED);
        super.validate(target, errors);
    }
}
