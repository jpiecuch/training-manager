package pl.jakubpiecuch.trainingmanager.dao;

import java.util.List;

public interface PageResult<T> {
    List<T> getResult();

    long getCount();
}

