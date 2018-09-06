package pl.jakubpiecuch.gymhome.dao;

import java.util.List;

public interface PageResult<T> extends CountResult {
    List<T> getResult();
}

