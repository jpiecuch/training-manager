package pl.jakubpiecuch.trainingmanager.web.controllers.api;

/**
 * Created by Rico on 2014-12-21.
 */
public class ApiURI {

    public static final String API_VERSION_PATH = "/api/{version}";
    private static final String USERS_PATH = "/users";
    public static final String LOCALE_PATH = "/locale";
    public static final String SIGN_IN_PATH = "/signin";
    public static final String RESOURCE_PATH = "/resources/{type}";
    public static final String LANGUAGE_PATH = "/languages";
    public static final String WORKOUTS_PATH = "/workouts";
    public static final String PLANS_PATH = "/plans";
    public static final String ROLE_PATH = "/roles";
    public static final String ACCOUNT_RECORD_PATH = "/records";
    public static final String KEY_PARAM = "key";
    public static final String KEY_PATH_PARAM = "/{" + KEY_PARAM + "}";
    public static final String ID_PARAM = "id";
    public static final String ID_PATH_PARAM = "/{" + ID_PARAM + "}";
    public static final String API_USERS_PATH = API_VERSION_PATH + USERS_PATH;
    public static final String API_LOCALE_PATH = API_VERSION_PATH + LOCALE_PATH;
    public static final String API_SIGN_IN_PATH = API_VERSION_PATH + SIGN_IN_PATH;
    public static final String API_RESOURCE_PATH = API_VERSION_PATH + RESOURCE_PATH;
    public static final String API_LANGUAGE_PATH = API_VERSION_PATH + LANGUAGE_PATH;
    public static final String API_PLAN_PATH = API_VERSION_PATH + PLANS_PATH;
    public static final String API_USERS_PLANS_PATH = API_USERS_PATH + ID_PATH_PARAM + PLANS_PATH;
    public static final String API_USERS_WORKOUTS_PATH = API_USERS_PATH + ID_PATH_PARAM + WORKOUTS_PATH;
    public static final String API_ROLE_PATH = API_VERSION_PATH + ROLE_PATH;
    public static final String API_ACCOUNT_RECORD_PATH = API_USERS_PATH + ID_PATH_PARAM + ACCOUNT_RECORD_PATH;
    public static final String API_RESET_PASSWORD = API_VERSION_PATH + "/reset";
    public static final String API_ACTIVATE_USER = API_VERSION_PATH + "/activate";
    private static final String DICTIONARY_PATH = "/dictionary";
    public static final String API_DICTIONARY_PATH = API_VERSION_PATH + DICTIONARY_PATH;
    private static final String DESCRIPTION_PATH = "/descriptions";
    public static final String API_DESCRIPTION_PATH = API_VERSION_PATH + DESCRIPTION_PATH;
    private static final String EQUIPMENT_PATH = "/equipments";
    public static final String API_EQUIPMENT_PATH = API_VERSION_PATH + EQUIPMENT_PATH;
    private static final String EXECUTION_PATH = "/executions";
    public static final String API_EXECUTION_PATH = API_VERSION_PATH + EXECUTION_PATH;


    private ApiURI() {
    }
}
