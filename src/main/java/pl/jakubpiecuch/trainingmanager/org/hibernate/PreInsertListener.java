package pl.jakubpiecuch.trainingmanager.org.hibernate;

import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import pl.jakubpiecuch.trainingmanager.domain.VersionedEntity;

import java.util.Date;

/**
 * Created by Rico on 2014-12-28.
 */
public class PreInsertListener implements PreInsertEventListener {

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        if (event.getEntity() instanceof VersionedEntity) {
            VersionedEntity record = (VersionedEntity) event.getEntity();
            record.setCreated(new Date());
        }
        return false;
    }
}
