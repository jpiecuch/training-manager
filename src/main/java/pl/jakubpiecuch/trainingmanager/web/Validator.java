package pl.jakubpiecuch.trainingmanager.web;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Rico on 2014-11-29.
 */
public interface Validator<T> {
    public boolean isValid(T object, Response response, String parentName);
}
