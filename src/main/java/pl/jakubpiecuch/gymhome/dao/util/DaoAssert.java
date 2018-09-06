package pl.jakubpiecuch.gymhome.dao.util;

import pl.jakubpiecuch.gymhome.domain.CommonEntity;
import pl.jakubpiecuch.gymhome.web.exception.notfound.NotFoundException;

/**
 * Created by jakub on 12.12.2015.
 */
public class DaoAssert {
    public static final String NOT_EXISTS_ERROR = "not.exists.error";

    private DaoAssert() {

    }

    public static <E extends CommonEntity> void notNull(E entity) {
        notNull(entity, NOT_EXISTS_ERROR);
    }

    public static <E extends CommonEntity> void notNull(E entity, String message) {
        if (entity == null) {
            throw new NotFoundException(message);
        }
    }
}
