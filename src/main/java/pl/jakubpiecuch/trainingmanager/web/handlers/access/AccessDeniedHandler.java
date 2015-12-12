package pl.jakubpiecuch.trainingmanager.web.handlers.access;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import pl.jakubpiecuch.trainingmanager.web.handlers.exception.RestExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Rico on 2015-03-01.
 */
public class AccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {

    private ObjectMapper mapper;

    public AccessDeniedHandler() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getOutputStream().println(mapper.writeValueAsString(new RestExceptionHandler.ErrorResource(e.getMessage())));
    }
}
