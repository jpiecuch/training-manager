package pl.jakubpiecuch.trainingmanager.web.controllers.api;

/**
 * Created by Rico on 2014-12-21.
 */
public class ApiURI {

    public static final String API_VERSION_PATH = "/api/{version}";

    public static final String LOCALE_PATH = "/locale";
    public static final String SIGN_IN_PATH = "/signin";

    public static final String API_LOCALE_PATH = API_VERSION_PATH + LOCALE_PATH;
    public static final String API_SIGN_IN_PATH = API_VERSION_PATH + SIGN_IN_PATH;
}
