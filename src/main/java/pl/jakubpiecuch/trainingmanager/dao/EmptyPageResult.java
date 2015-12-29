package pl.jakubpiecuch.trainingmanager.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jakub on 26.12.2015.
 */
public class EmptyPageResult<T> implements PageResult<T> {
    @Override
    public List<T> getResult() {
        return new ArrayList<>();
    }

    @Override
    public long getCount() {
        return 0;
    }
}
