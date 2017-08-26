package pl.jakubpiecuch.gymhome.service.flow.plan;

import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Validator;
import pl.jakubpiecuch.gymhome.domain.CommonEntity;
import pl.jakubpiecuch.gymhome.domain.Plan;
import pl.jakubpiecuch.gymhome.web.exception.validator.ValidationException;
import pl.jakubpiecuch.gymhome.web.validator.RestrictionCode;

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

        try {
            getValidator().validate(getPlan(), errors);
            fail();
        } catch (ValidationException ex) {
            assertTrue(errors.hasErrors());
            assertEquals(3, errors.getFieldErrorCount());

            ValidationTestUtils.AssertBuilder assertBuilder = ValidationTestUtils.createAssertBuilder();

            for (String fullField : new String[]{CommonEntity.ID_FIELD_NAME, InsertPlanValidator.PHASES_FIELD, Plan.NAME_FIELD, InsertPlanValidator.PHASES_FIELD}) {
                assertBuilder.addAssert(fullField, RestrictionCode.REQUIRED);
            }
        }

        getPlan().setId(1l);

        super.testValidate();
    }

}