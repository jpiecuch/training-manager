package pl.jakubpiecuch.trainingmanager.dao.plans.impl;

import pl.jakubpiecuch.trainingmanager.dao.hibernate.HibernateDaoSupport;
import pl.jakubpiecuch.trainingmanager.dao.plans.CalendarsDao;
import pl.jakubpiecuch.trainingmanager.domain.plans.Calendars;

public class CalendarsDaoImpl extends HibernateDaoSupport implements CalendarsDao {

    @Override
    public Calendars findByUser(Long id, String userName) {
        if (id != null) {
            return (Calendars) session().createQuery("SELECT c FROM Calendars c LEFT JOIN FETCH c.dayExercisesList WHERE c.user.id = :id").setParameter("id", id).uniqueResult();
        } else if(userName != null) {
            return (Calendars) session().createQuery("SELECT c FROM Calendars c LEFT JOIN FETCH c.dayExercisesList WHERE c.user.name = :name").setParameter("name", userName).uniqueResult();
        }
        return null;
    }
}
