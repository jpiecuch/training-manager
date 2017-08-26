package pl.jakubpiecuch.gymhome.web.handlers.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.jakubpiecuch.gymhome.web.exception.notfound.NotFoundException;
import pl.jakubpiecuch.gymhome.web.exception.validator.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Rico on 2014-12-20.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler({ValidationException.class})
    protected ResponseEntity<Object> handleValidation(RuntimeException e, WebRequest request) {
        LOGGER.error("", e);
        ValidationException ire = (ValidationException) e;

        ErrorResource error = new ErrorResource(ire.getMessage());

        for (FieldError fe : ire.getErrors().getFieldErrors()) {
            error.addFieldError(new ErrorResource.FieldError(fe.getField(), fe.getCode(), ArrayUtils.isNotEmpty(fe.getArguments()) ? (Map<String, Object>) fe.getArguments()[0] : null));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return super.handleExceptionInternal(e, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    protected ResponseEntity<Object> handleInvalidRequest(RuntimeException e, WebRequest request) {
        LOGGER.error("", e);
        IllegalArgumentException ire = (IllegalArgumentException) e;

        ErrorResource error = new ErrorResource(ire.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return super.handleExceptionInternal(e, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({NotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(RuntimeException e, WebRequest request) {
        LOGGER.error("", e);

        ErrorResource error = new ErrorResource(e.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return super.handleExceptionInternal(e, error, headers, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDenied(RuntimeException e, WebRequest request) {
        LOGGER.error("", e);

        ErrorResource error = new ErrorResource(e.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return super.handleExceptionInternal(e, error, headers, HttpStatus.FORBIDDEN, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error("", ex);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return super.handleExceptionInternal(ex, body == null ? new ErrorResource(ex.getMessage()) : body, headers, status, request);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ErrorResource {
        private String message;
        private List<FieldError> fieldErrors;

        public ErrorResource(String message) {
            this.message = message;
        }

        public void addFieldError(FieldError error) {
            if (fieldErrors == null) {
                fieldErrors = new ArrayList<FieldError>();
            }
            fieldErrors.add(error);
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<FieldError> getFieldErrors() {
            return fieldErrors;
        }

        public static class FieldError {
            private final String field;
            private final String code;
            private final Map<String, Object> params;

            public FieldError(String field, String code, Map<String, Object> params) {
                this.field = field;
                this.code = code;
                this.params = params;
            }

            public String getCode() {
                return code;
            }

            public String getField() {
                return field;
            }

            public Map<String, Object> getParams() {
                return params;
            }
        }

    }
}
