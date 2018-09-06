package pl.jakubpiecuch.gymhome.service.flow.plan;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.annotation.Nullable;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class ValidationTestUtils {
    private ValidationTestUtils() {
    }

    public static String[] extendFields(final FieldPathBuilder builder, String... fields) {
        return Lists.transform(Lists.newArrayList(fields), new Function<String, String>() {
            @Nullable
            @Override
            public String apply(@Nullable String string) {
                return builder.build(string);
            }
        }).toArray(new String[fields.length]);
    }

    public static AssertBuilder createAssertBuilder() {
        return new AssertBuilder();
    }

    public static FieldPathBuilder newInstance() {
        return new FieldPathBuilder();
    }

    public static class FieldPathBuilder {

        private static final String DELIMITER = ".";
        private static final char OPEN_ARRAY = '[';
        private static final char CLOSE_ARRAY = ']';
        private StringBuilder builder = new StringBuilder();

        private FieldPathBuilder() {

        }

        public FieldPathBuilder addField(String field) {
            builder.append(builder.length() > 0 ? DELIMITER : StringUtils.EMPTY).append(field);
            return this;
        }

        public FieldPathBuilder addCollectionField(String field, int index) {
            builder.append(builder.length() > 0 ? DELIMITER : StringUtils.EMPTY).append(field).append(OPEN_ARRAY).append(index).append(CLOSE_ARRAY);
            return this;
        }

        public String build() {
            return builder.toString();
        }

        public String build(String suffix) {
            return build() + DELIMITER + suffix;
        }

        public String build(String suffix, int index) {
            return build(suffix) + OPEN_ARRAY + index + CLOSE_ARRAY;
        }

    }

    public static class AssertBuilder {

        private Map<String, List<String>> assertions = new HashMap<String, List<String>>();

        private AssertBuilder() {

        }

        public AssertBuilder addAssert(String field, String... restrictions) {
            assertions.put(field, Arrays.asList(restrictions));
            return this;
        }

        public void assertion(Errors errors) {
            for (Map.Entry<String, List<String>> entry : assertions.entrySet()) {
                List<String> expected = entry.getValue();

                List<String> actual = Lists.transform(errors.getFieldErrors(entry.getKey()), new Function<FieldError, String>() {
                    @Nullable
                    @Override
                    public String apply(@Nullable FieldError fieldError) {
                        return fieldError.getCode();
                    }
                });

                assertEquals(String.format("Expected errors for field %s are different than actual", entry.getKey()), expected, actual);
            }


            assertEquals("Not all fields were assert", new HashSet<String>(Lists.transform(errors.getFieldErrors(), new Function<FieldError, String>() {
                @Nullable
                @Override
                public String apply(@Nullable FieldError fieldError) {
                    return fieldError.getField();
                }
            })), assertions.keySet());
        }
    }
}