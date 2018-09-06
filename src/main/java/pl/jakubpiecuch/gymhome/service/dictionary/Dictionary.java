package pl.jakubpiecuch.gymhome.service.dictionary;

import java.util.List;
import java.util.Map;

/**
 * Created by Rico on 2015-01-01.
 */
public interface Dictionary {

    List retrieve(long id);
    Map<Long, List> retrieve(long[] ids);
}
