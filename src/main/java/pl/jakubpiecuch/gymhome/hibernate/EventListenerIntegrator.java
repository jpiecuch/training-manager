package pl.jakubpiecuch.gymhome.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;

import javax.annotation.PostConstruct;

/**
 * Created by Rico on 2014-12-28.
 */
public class EventListenerIntegrator {

    private SessionFactory sessionFactory;

    @PostConstruct
    public void registerListeners() {
        SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) sessionFactory;
        EventListenerRegistry registry = sessionFactoryImpl.getServiceRegistry().getService(EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.PRE_INSERT).appendListener(new PreInsertListener());
        registry.getEventListenerGroup(EventType.PRE_UPDATE).appendListener(new PreUpdateListener());
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
