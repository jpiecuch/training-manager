package pl.jakubpiecuch.gymhome.web.validator;

/**
 * Created by Rico on 2014-11-29.
 */
public class RestrictionCode {
    public static final String REQUIRED = "required";
    public static final String PATTERN = "pattern";
    public static final String EQUAL = "equal";
    public static final String MIN_LENGTH = "minLength";
    public static final String MAX_LENGTH = "maxLength";
    public static final String INVALID = "invalid";
    public static final String LOWER = "lower than";
    public static final String UNIQUE = "unique";
    public static final String CHECKED = "checked";
    public static final String EXISTS = "exists";
    public static final String INVALID_ORDER = "invalidOrder";
    public static final String PRECISION = "precision";

    private RestrictionCode() {
    }
}
