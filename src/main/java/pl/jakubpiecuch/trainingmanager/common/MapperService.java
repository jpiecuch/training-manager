package pl.jakubpiecuch.trainingmanager.common;

import java.io.InputStream;

/**
 * Created by Rico on 2014-11-22.
 */
public interface MapperService {

    <T> T getObject(InputStream stream, Class<T> clazz) throws Exception;
}
