package pl.jakubpiecuch.trainingmanager.service.user;

/**
 * Created by Rico on 2014-11-22.
 */
public interface UserManageService {

    enum ResetStatus { OK,USER_NOT_EXIST }

    ResetStatus password(String id);
    boolean activate(String id);
    boolean availability(String field, String value);
}
