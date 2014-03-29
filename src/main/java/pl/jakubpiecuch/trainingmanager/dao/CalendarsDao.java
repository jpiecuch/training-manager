package pl.jakubpiecuch.trainingmanager.dao;

import pl.jakubpiecuch.trainingmanager.dao.core.CoreDao;
import pl.jakubpiecuch.trainingmanager.domain.Calendars;

public interface CalendarsDao extends CoreDao {
    Calendars findByUser(Long id, String userName);
}
