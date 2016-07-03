package pl.jakubpiecuch.trainingmanager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * Created by jakub on 19.09.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/context/unit/hsql-test-datasource-context.xml"})
public abstract class BaseUnitDaoTestCase<T extends CommonEntity> extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String NOT_NULL_MESSAGE = "may not be null";

    @Test
    public void shouldValidateEntityNotNullConstraints() {
        testConstraints(getEntity(), generateViolations(getNotNullProperties(), NOT_NULL_MESSAGE));
    }

    protected void testConstraints(T entity, List<String> violations) {
        try {
            getDao().create(entity);
            fail();
        } catch (ConstraintViolationException ex) {
            assertEquals(violations.size(), ex.getConstraintViolations().size());
            for (ConstraintViolation violation : ex.getConstraintViolations()) {
                String actualViolation = getConstrainMessage(violation.getMessage(), violation.getPropertyPath().toString());
                assertTrue(String.format("Violation %s is not expected.", actualViolation), violations.contains(actualViolation));
            }
        }
    }

    protected List<String> generateViolations(List<String> properties, String message) {
        return properties.stream().map(property -> getConstrainMessage(message, property)).collect(Collectors.toList());
    }

    private String getConstrainMessage(String message, String property) {
        return property + ": " + message;
    }

    protected abstract List<String> getNotNullProperties();
    protected abstract CoreDao<T> getDao();
    protected abstract T getEntity();
}
