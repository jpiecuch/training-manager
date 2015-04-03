package pl.jakubpiecuch.trainingmanager.domain;

/**
 * Created by Rico on 2015-03-27.
 */
public class Permissions {

    public static final String IS_AUTHENTICATED = "isAuthenticated()";
    public static final String IS_ANONYMOUS = "isAnonymous()";

    public static final String HAS_ROLE_PREFIX = "hasRole('";
    public static final String HAS_ROLE_SUFFIX = "')";

    public static final String PLAN_CREATOR  = "PLAN_C";
    public static final String PLAN_UPDATER  = "PLAN_U";
    public static final String PLAN_DELETER  = "PLAN_D";
    public static final String PLAN_VIEWER   = "PLAN_V";
    public static final String PLAN_APPROVER = "PLAN_A";
    public static final String PLAN_STARTER = "PLAN_S";
    public static final String DESCRIPTION_CREATOR  = "DESCRIPTION_C";
    public static final String DESCRIPTION_UPDATER  = "DESCRIPTION_U";
    public static final String DESCRIPTION_DELETER  = "DESCRIPTION_D";
    public static final String DESCRIPTION_VIEWER   = "DESCRIPTION_V";
    public static final String DESCRIPTION_APPROVER = "DESCRIPTION_A";
    public static final String EQUIPMENT_CREATOR  = "EQUIPMENT_C";
    public static final String EQUIPMENT_UPDATER  = "EQUIPMENT_U";
    public static final String EQUIPMENT_DELETER  = "EQUIPMENT_D";
    public static final String EQUIPMENT_VIEWER   = "EQUIPMENT_V";
    public static final String EQUIPMENT_APPROVER = "EQUIPMENT_A";
    public static final String EXECUTION_CREATOR = "EXECUTION_C";
    public static final String ROLE_VIEWER = "ROLE_V";
    public static final String ROLE_CREATOR = "ROLE_C";
    public static final String ROLE_UPDATER = "ROLE_U";
    public static final String ROLE_DELETER = "ROLE_D";
    public static final String WORKOUT_VIEWER = "WORKOUT_V";

    public static final String[] ALL = new String[] { PLAN_CREATOR, PLAN_UPDATER, PLAN_DELETER, PLAN_VIEWER, PLAN_APPROVER, PLAN_STARTER,
            EQUIPMENT_CREATOR, EQUIPMENT_UPDATER, EQUIPMENT_DELETER, EQUIPMENT_VIEWER, EQUIPMENT_APPROVER,
            DESCRIPTION_CREATOR, DESCRIPTION_UPDATER, DESCRIPTION_DELETER, DESCRIPTION_VIEWER, DESCRIPTION_APPROVER,
            ROLE_CREATOR, ROLE_UPDATER, ROLE_DELETER, ROLE_VIEWER,
            WORKOUT_VIEWER,
            EXECUTION_CREATOR };

    public static final String[] USER_ROLE_PERMISSIONS = new String[] { PLAN_VIEWER, DESCRIPTION_VIEWER };


    private Permissions() {
    }
}
