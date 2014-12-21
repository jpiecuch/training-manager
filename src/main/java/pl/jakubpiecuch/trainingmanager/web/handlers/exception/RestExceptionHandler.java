package pl.jakubpiecuch.trainingmanager.web.handlers.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.jakubpiecuch.trainingmanager.web.exception.validator.ValidationException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rico on 2014-12-20.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ErrorResource {
        protected final static String DEFAULT_CODE = "InvalidRequest";
        private String code;
        private String message;
        private List<FieldError> fieldErrors;

        public ErrorResource(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public void addFieldError(FieldError error) {
            if (fieldErrors == null) {
                fieldErrors = new ArrayList<FieldError>();
            }
            fieldErrors.add(error);
        }

        public String getCode() { return code; }

        public void setCode(String code) { this.code = code; }

        public String getMessage() { return message; }

        public void setMessage(String message) { this.message = message; }

        public List<FieldError> getFieldErrors() {
            return fieldErrors;
        }

        public static class FieldError {
            private String field;
            private String code;

            public FieldError(String field, String code) {
                this.field = field;
                this.code = code;
            }

            public String getCode() {
                return code;
            }

            public String getField() {
                return field;
            }
        }

    }

    @ExceptionHandler({ ValidationException.class})
    protected ResponseEntity<Object> handleValidation(RuntimeException e, WebRequest request) {
        ValidationException ire = (ValidationException) e;

        ErrorResource error = new ErrorResource(ErrorResource.DEFAULT_CODE, ire.getMessage());

        for(FieldError fe : ire.getErrors().getFieldErrors()) {
            error.addFieldError(new ErrorResource.FieldError(fe.getField(), fe.getCode()));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return super.handleExceptionInternal(e, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ IllegalArgumentException.class})
    protected ResponseEntity<Object> handleInvalidRequest(RuntimeException e, WebRequest request) {
        IllegalArgumentException ire = (IllegalArgumentException) e;

        ErrorResource error = new ErrorResource(ErrorResource.DEFAULT_CODE, ire.getMessage());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return super.handleExceptionInternal(e, error, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        return super.handleExceptionInternal(ex, body == null ? new ErrorResource(ErrorResource.DEFAULT_CODE, ex.getMessage()) : body, headers, status, request);
    }
}
