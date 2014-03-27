package pl.jakubpiecuch.trainingmanager.dao.plans;

import pl.jakubpiecuch.trainingmanager.domain.plans.Calendars;

public interface CalendarsDao {
    Calendars findByUser(Long id, String userName);
}
