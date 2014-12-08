package pl.jakubpiecuch.trainingmanager.web.validator;

import org.apache.commons.lang.StringUtils;
import pl.jakubpiecuch.trainingmanager.web.Response;

/**
 * Created by Rico on 2014-11-29.
 */
public class ValidationUtils {

    public interface Restriction {
        String REQUIRED = "required";
        String INCOMPATIBLE = "incompatible";
        String PATTERN = "pattern";
        String EQUAL = "equal";
        String MIN_LENGTH = "minLength";
        String MAX_LENGTH = "maxLength";
    }

    public static void rejectIfEmpty(String field, Response response, String fieldName) {
        if (StringUtils.isEmpty(field)) {
            response.addError(new Response.Error.Builder(Response.Error.Type.VALIDATION).property(fieldName).restriction(Restriction.REQUIRED).build());
        }
    }
}
