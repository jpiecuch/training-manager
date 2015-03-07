package pl.jakubpiecuch.trainingmanager.service.locale.cookie;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.Assert;
import org.springframework.web.servlet.LocaleResolver;
import pl.jakubpiecuch.trainingmanager.service.locale.LocaleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * {@link pl.jakubpiecuch.trainingmanager.service.locale.LocaleService} implementations that
 * uses a {@link org.springframework.web.servlet.LocaleResolver} as locale updater. LocaleResolver
 * can be injected with any proper implementation.
 * Created by Rico on 2014-12-19.
 */
public class ResolverLocaleService implements LocaleService {

    LocaleResolver localeResolver;
    private Locale[] supportedLocales;

    /**
     * @see pl.jakubpiecuch.trainingmanager.service.locale.LocaleService#update(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.util.Locale)
     */
    @Override
    public void update(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        Assert.notNull(locale);
        validateIfLocaleIsSupported(locale);
        localeResolver.setLocale(request, response, locale);
    }

    private void validateIfLocaleIsSupported(Locale locale) {
        if (!ArrayUtils.contains(supportedLocales, locale)) {
            throw new IllegalArgumentException("Locale: " + locale + " is not supported.");
        }
    }

    public void setLocaleResolver(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    public void setSupportedLocales(String[] locales) {
        this.supportedLocales = new Locale[locales.length];
        for (int i =0; i < locales.length; i++) {
            supportedLocales[i] = new Locale(locales[i]);
        }
    }
}
