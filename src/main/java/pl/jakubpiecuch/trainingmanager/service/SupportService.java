package pl.jakubpiecuch.trainingmanager.service;

import java.util.List;

/**
 * Created by Rico on 2014-11-22.
 */
public interface SupportService<T> {

    public List<T> supported();
}
