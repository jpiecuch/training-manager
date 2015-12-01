package pl.jakubpiecuch.trainingmanager.service.flow.plan;

import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import pl.jakubpiecuch.trainingmanager.domain.Plan;
import pl.jakubpiecuch.trainingmanager.web.exception.validator.ValidationException;
import pl.jakubpiecuch.trainingmanager.web.validator.RestrictionCode;

import static org.junit.Assert.*;

/**
 * Created by jpiecuch on 2015-11-20.
 */
public class PlanValidatorTest {

    private static final java.lang.String NAME = "plan";
    private PlanValidator validator = new PlanValidator();

    @Test
    public void testValidate() throws Exception {
        PlanDto plan = new PlanDto();
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(plan, NAME);

        boolean assertionPerformed = false;
        try {
            validator.validate(plan, errors);
        } catch (ValidationException ex) {
            assertTrue(ex.getErrors().hasErrors());

            assertRequiredField(ex.getErrors(), Plan.NAME_FIELD);
            assertRequiredField(ex.getErrors(), PlanValidator.PHASES_FIELD);

            assertionPerformed = true;
        }

        assertTrue(assertionPerformed);

    }

    private void assertRequiredField(Errors errors, String field) {
        assertEquals(1, errors.getFieldErrors(field).size());
        assertEquals(field, errors.getFieldErrors(field).get(0).getField());
        assertEquals(RestrictionCode.REQUIRED, errors.getFieldErrors(field).get(0).getCode());
    }
}