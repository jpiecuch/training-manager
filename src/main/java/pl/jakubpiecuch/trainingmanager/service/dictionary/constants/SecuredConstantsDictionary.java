package pl.jakubpiecuch.trainingmanager.service.dictionary.constants;

import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by Rico on 2015-01-01.
 */
public class SecuredConstantsDictionary extends ConstantsDictionary{

    @Override
    @PreAuthorize("isAuthenticated()")
    public Object retrieve(long id) {
        return map.get(id);
    }
}
