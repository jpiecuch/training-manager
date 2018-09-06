package pl.jakubpiecuch.gymhome.web.authentication;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import pl.jakubpiecuch.gymhome.web.handlers.exception.RestExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Rico on 2015-03-01.
 */
public class JsonAuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

    private ObjectMapper mapper;

    public JsonAuthenticationEntryPoint() {
        mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getOutputStream().println(mapper.writeValueAsString(new RestExceptionHandler.ErrorResource(authException.getMessage())));

    }
}
