package pl.jakubpiecuch.trainingmanager.web.validator;

import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import pl.jakubpiecuch.trainingmanager.AbstractBaseTest;
import pl.jakubpiecuch.trainingmanager.domain.Exercises;

public class ExerciseValidatorTest extends AbstractBaseTest {

    @Autowired
    private ExerciseValidator exerciseValidator;

    @Test
    public void validateTest() {
        Exercises e = new Exercises();
        Errors errors = new BeanPropertyBindingResult(e, "exercise");

        exerciseValidator.validate(e, errors);

        assertTrue(errors.hasErrors());

        assertEquals(3, errors.getFieldErrorCount());

    }

}
