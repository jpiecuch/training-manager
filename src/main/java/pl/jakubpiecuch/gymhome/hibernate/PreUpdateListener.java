package pl.jakubpiecuch.gymhome.hibernate;

import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import pl.jakubpiecuch.gymhome.domain.VersionedEntity;

import java.util.Date;

/**
 * Created by Rico on 2014-12-28.
 */
public class PreUpdateListener implements PreUpdateEventListener {

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        if (event.getEntity() instanceof VersionedEntity) {
            VersionedEntity record = (VersionedEntity) event.getEntity();
            record.setUpdated(new Date());
        }
        return false;
    }
}
