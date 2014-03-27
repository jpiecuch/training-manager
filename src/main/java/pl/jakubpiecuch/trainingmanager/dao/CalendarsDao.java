package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.domain.Calendars;

public interface CalendarsDao {
    Calendars findByUser(Long id, String userName);
}
