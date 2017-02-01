package pl.jakubpiecuch.trainingmanager.dao;

import java.util.List;

public interface PageResult<T> extends CountResult {
    List<T> getResult();
}

