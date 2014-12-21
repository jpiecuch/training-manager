package pl.jakubpiecuch.trainingmanager.service.locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Interface constains base set of methods for manipulating locale.
 * Created by Rico on 2014-12-19.
 */
public interface LocaleService {

    /**
     * Set the current locale to the given one.
     * @param locale new locale
     * @param request the request to be used for locale modification
     * @param response the response to be used for locale modification
     */
    public void update(HttpServletRequest request, HttpServletResponse response, Locale locale) throws IllegalArgumentException ;
}
