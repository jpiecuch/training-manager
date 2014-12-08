package pl.jakubpiecuch.trainingmanager.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.collections.MapUtils;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by Rico on 2014-11-29.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private Map<Error.Type, Set<Error>> errors = null;
    private T entity = null;

    public Map<Error.Type, Set<Error>> getErrors() {
        return errors;
    }

    public T getEntity() {
        return entity;
    }

    public void addError(Error error) {
        if (entity != null) {
            throw new IllegalArgumentException("Entity response already exists");
        }
        if (errors ==  null) {
            errors = new HashMap<Error.Type, Set<Error>>();
        }
        if (errors.get(error.type) == null) {
            errors.put(error.type, new HashSet<Error>());
        }
        errors.get(error.type).add(error);
    }

    public void addEntity(T entity) {
        if (errors != null) {
            throw new IllegalArgumentException("Errors response already exists");
        }
        this.entity = entity;
    }

    public Response updateHttpStatus(HttpServletResponse response) {
       response.setStatus(this.hasErrors() ? HttpStatus.BAD_REQUEST.value() : HttpStatus.OK.value());
       return this;
    }

    public boolean hasErrors() {
        return MapUtils.isNotEmpty(errors);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Error {
        public interface Code {
            String MARSHALLING = "wrong input data";
            String ACCOUNT_INVALID = "acount not exist or is inactive";
        }
        public enum Type { VALIDATION, RUNTIME }
        private String property;
        private String restriction;
        private String value;
        private Type type;

        private Error(Builder builder) {
            this.property = builder.property;
            this.restriction = builder.restriction;
            this.value = builder.value;
            this.type = builder.type;
        }

        public String getProperty() {
            return property;
        }

        public String getRestriction() {
            return restriction;
        }

        public String getValue() {
            return value;
        }

        public static class Builder {
            Type type;
            String property;
            String restriction;
            String value;

            public Builder(Type type) {
                this.type = type;
            }

            public Builder property(String property) {
                if (Type.RUNTIME == this.type) {
                    throw new IllegalArgumentException("Runtime exception can hold only value");
                }
                this.property = property;
                return this;
            }

            public Builder restriction(String restriction) {
                if (Type.RUNTIME == this.type) {
                    throw new IllegalArgumentException("Runtime exception can hold only value");
                }
                this.restriction = restriction;
                return this;
            }

            public Builder value(String value) {
                this.value = value;
                return this;
            }

            public Error build() {
                return new Error(this);
            }
        }
    }
}
