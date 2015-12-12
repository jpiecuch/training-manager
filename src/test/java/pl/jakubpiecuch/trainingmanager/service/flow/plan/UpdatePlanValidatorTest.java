package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.web.exception.validator.ValidationException;

import static org.junit.Assert.*;

/**
 * Created by jakub on 12.12.2015.
 */
public class UpdatePlanValidatorTest extends InsertPlanValidatorTest {

    private UpdatePlanValidator validator = new UpdatePlanValidator();
    private PlanDto plan = new PlanDto();

    @Override
    public PlanDto getPlan() {
        return plan;
    }

    @Override
    public Validator getValidator() {

        return validator;
    }

    @Test
    public void testValidate() throws Exception {
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(getPlan(), NAME);

        boolean assertionPerformed = false;
        try {
            getValidator().validate(getPlan(), errors);
        } catch (ValidationException ex) {
            assertTrue(errors.hasErrors());
            assertEquals(3, errors.getFieldErrorCount());

            assertRequiredFields(errors, CommonEntity.ID_FIELD_NAME, InsertPlanValidator.PHASES_FIELD, Plan.NAME_FIELD, InsertPlanValidator.PHASES_FIELD);

            assertionPerformed = true;
        }

        assertTrue(assertionPerformed);

        getPlan().setId(1l);

        super.testValidate();
    }

}