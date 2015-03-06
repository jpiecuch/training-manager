package pl.jakubpiecuch.trainingmanager.web.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import java.io.IOException;

public class WebUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebUtil.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void authenticate(UserDetails userDetails) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        AbstractAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        securityContext.setAuthentication(auth);
        auth.setDetails(userDetails);
    }

    public static Integer[] toIntArray(String[] array) {
        Integer res[] = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = Integer.parseInt(array[i]);
        }
        return res;
    }

    public static <T> T fromJson(String data, Class<T> outputClass) throws IOException {
        return mapper.readValue(data, outputClass);

    }

    public static String toJson(Object data) {
        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            LOGGER.warn("", e);
            return null;
        }
    }

    public static Double[] toDoubleArray(String[] array) {
        Double[] res = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            res[i] = Double.parseDouble(array[i]);
        }
        return res;
    }
}
