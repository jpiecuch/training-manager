package pl.jakubpiecuch.trainingmanager.service.support;

import java.util.List;

/**
 * Created by Rico on 2014-11-22.
 */
public interface SupportService<T> {

    List<T> supported();
}
