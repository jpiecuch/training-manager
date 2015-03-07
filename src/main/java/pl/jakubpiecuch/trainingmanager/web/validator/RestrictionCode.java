package pl.jakubpiecuch.trainingmanager.web.validator;

/**
 * Created by Rico on 2014-11-29.
 */
public class RestrictionCode {
    public static final String REQUIRED = "required";
    public static final String PATTERN = "pattern";
    public static final String EQUAL = "equal";
    public static final String MIN_LENGTH = "minLength: %s";
    public static final String MAX_LENGTH = "maxLength: %s";
    public static final String INVALID = "invalid";
    public static final String LOWER = "lower than: %s";

    private RestrictionCode() {
    }
}
