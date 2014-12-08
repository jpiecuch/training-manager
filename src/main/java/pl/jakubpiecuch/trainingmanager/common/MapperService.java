package pl.jakubpiecuch.trainingmanager.common;

import pl.jakubpiecuch.trainingmanager.service.user.Authentication;
import pl.jakubpiecuch.trainingmanager.web.Response;

import java.io.InputStream;

/**
 * Created by Rico on 2014-11-22.
 */
public interface MapperService {

    <T> T getObject(InputStream stream, Class<T> clazz) throws Exception;

    <T> T getObject(InputStream stream, Class<T> clazz, Response<T> response);
}
