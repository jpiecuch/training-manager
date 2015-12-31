package pl.jakubpiecuch.trainingmanager.service.dictionary.constants;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Map;

/**
 * Created by Rico on 2015-01-01.
 */
public class SecuredConstantsDictionary extends ConstantsDictionary {

    @Override
    @PreAuthorize("isAuthenticated()")
    public List retrieve(long id) {
        return map.get(id);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public Map<Long, List> retrieve(final long[] ids) {
        return Maps.filterEntries(map, input -> ArrayUtils.contains(ids, input.getKey()));
    }
}
