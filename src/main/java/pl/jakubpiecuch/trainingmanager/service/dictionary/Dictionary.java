package pl.jakubpiecuch.trainingmanager.service.dictionary;

/**
 * Created by Rico on 2015-01-01.
 */
public interface Dictionary {

    Object retrieve(long id);
    Object retrieve(Long[] ids);
}
