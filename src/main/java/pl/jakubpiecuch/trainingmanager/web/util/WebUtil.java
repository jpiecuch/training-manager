package pl.jakubpiecuch.trainingmanager.web.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;
import pl.jakubpiecuch.trainingmanager.service.resource.ResourceService;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String extractPathFromPattern(final HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);

        AntPathMatcher apm = new AntPathMatcher();
        return apm.extractPathWithinPattern(bestMatchPattern, path);
    }

    public static ResourceService.Type resolveResourceType(String extension) {
        for (ResourceService.Type t : ResourceService.Type.values()) {
            if (ArrayUtils.contains(t.extensions, extension)) {
                return t;
            }
        }
        return null;
    }

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

    public static <T> T fromJson(String data, Class<T> outputClass) throws IllegalArgumentException {
        try {
            return mapper.readValue(data, outputClass);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    public static String toJson(Object data) {
        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
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
