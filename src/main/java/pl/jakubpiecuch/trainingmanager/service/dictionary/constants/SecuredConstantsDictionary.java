package pl.jakubpiecuch.trainingmanager.service.dictionary.constants;

import com.google.common.base.Predicate;
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
    public Object retrieve(long id) {
        return map.get(id);
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public Object retrieve(final Long[] ids) {
        return Maps.filterEntries(map, new Predicate<Map.Entry<Long, List>>() {
            @Override
            public boolean apply(Map.Entry<Long, List> input) {
                return ArrayUtils.contains(ids, input.getKey());
            }
        });
    }
}
