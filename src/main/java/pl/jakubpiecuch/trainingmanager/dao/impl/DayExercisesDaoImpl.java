package pl.jakubpiecuch.trainingmanager.dao.impl;

import java.util.Date;
import java.util.List;
import pl.jakubpiecuch.trainingmanager.dao.DayExercisesDao;
import pl.jakubpiecuch.trainingmanager.dao.core.impl.CoreDaoImpl;
import pl.jakubpiecuch.trainingmanager.domain.DayExercises;

public class DayExercisesDaoImpl extends CoreDaoImpl  implements DayExercisesDao {

    private static final String LEFT_JOIN_BENCHES = " LEFT JOIN FETCH d.benches bl LEFT JOIN FETCH bl.lengthOfUnit bllof LEFT JOIN FETCH bl.heightUnit blhu ";
    private static final String LEFT_JOIN_DUMBBELLS = " LEFT JOIN FETCH d.dumbbells dl LEFT JOIN FETCH dl.weightUnit dlwu LEFT JOIN FETCH dl.diameterUnit dldu LEFT JOIN FETCH dl.lengthOfUnit dllu ";
    private static final String LEFT_JOIN_LOADS = " LEFT JOIN FETCH d.loads ll LEFT JOIN FETCH ll.weightUnit llwu LEFT JOIN FETCH ll.holeDiameterUnit lldu ";
    private static final String LEFT_JOIN_NECKS = " LEFT JOIN FETCH d.necks nl LEFT JOIN FETCH nl.weightUnit nlwu LEFT JOIN FETCH nl.diameterUnit nldu LEFT JOIN FETCH nl.lengthOfUnit nllou ";
    private static final String LEFT_JOIN_STANDS = " LEFT JOIN FETCH d.stands sl LEFT JOIN FETCH sl.heightMaxUnit slhmu LEFT JOIN FETCH sl.heightMinUnit slhmiu ";
    private static final String LEFT_JOIN_BARS = " LEFT JOIN FETCH d.bars brl LEFT JOIN FETCH brl.strengthUnit brls LEFT JOIN FETCH brl.lengthOfUnit brll ";
    private static final String LEFT_JOIN_PRESS = " LEFT JOIN FETCH d.press pl LEFT JOIN FETCH pl.strengthUnit pls ";
    private static final String LEFT_JOIN_EXERCISE = " LEFT JOIN FETCH d.exercise e ";

    @Override
    public DayExercises findById(Long id) {
        return (DayExercises) session().
                createQuery("SELECT d FROM DayExercises d" + LEFT_JOIN_EXERCISE + LEFT_JOIN_BENCHES + LEFT_JOIN_DUMBBELLS + LEFT_JOIN_LOADS
                        + LEFT_JOIN_NECKS + LEFT_JOIN_STANDS + LEFT_JOIN_BARS + LEFT_JOIN_PRESS + "WHERE d.id = :id").setParameter("id", id).uniqueResult();
    }

    @Override
    public List<DayExercises> findByCalendarId(Long calendarId) {
        return session().createQuery("SELECT d FROM DayExercises d" + LEFT_JOIN_EXERCISE + "WHERE d.calendar.id = :calendarId ORDER BY d.date, d.position")
                .setParameter("calendarId", calendarId).list();
    }

    @Override
    public List<DayExercises> findByCalendarIdAndDate(Long calendarId, Date date) {
        return session().createQuery("SELECT DISTINCT(d) FROM DayExercises d" + LEFT_JOIN_EXERCISE + LEFT_JOIN_BENCHES + LEFT_JOIN_DUMBBELLS + LEFT_JOIN_LOADS
                + LEFT_JOIN_NECKS + LEFT_JOIN_STANDS + LEFT_JOIN_BARS + LEFT_JOIN_PRESS + "WHERE d.calendar.id = :calendarId AND d.date = :date ORDER BY d.position")
                .setParameter("calendarId", calendarId).setDate("date", date).list();
    }

    @Override
    public Long countByUserIdAndDate(Long id, Date date) {
        return (Long) session().createQuery("SELECT COUNT(DISTINCT d) FROM DayExercises d WHERE d.calendar.user.id = :userId AND d.date = :date")
                .setParameter("userId", id).setDate("date", date).uniqueResult();
    }

    @Override
    public List<DayExercises> findByCalendarIdAndExerciseId(Long calendarId, Long exerciseId) {
        return session().createQuery("SELECT d FROM DayExercises d" + LEFT_JOIN_EXERCISE + LEFT_JOIN_BENCHES + LEFT_JOIN_DUMBBELLS + LEFT_JOIN_LOADS
                + LEFT_JOIN_NECKS + LEFT_JOIN_STANDS + LEFT_JOIN_BARS + LEFT_JOIN_PRESS + "WHERE d.calendar.id = :calendarId AND d.exercise.id = :exerciseId ORDER BY d.date ASC")
                .setParameter("calendarId", calendarId).setParameter("exerciseId", exerciseId).list();
          }

    @Override
    public DayExercises findLastDayExercise(Long exerciseId, Date date) {
        List<DayExercises> result = session().createQuery("SELECT DISTINCT(d) FROM DayExercises d" + LEFT_JOIN_EXERCISE + LEFT_JOIN_BENCHES + LEFT_JOIN_DUMBBELLS + LEFT_JOIN_LOADS
                + LEFT_JOIN_NECKS + LEFT_JOIN_STANDS + LEFT_JOIN_BARS + LEFT_JOIN_PRESS + "WHERE d.exercise.id = :exerciseId AND d.date < :date ORDER BY d.date DESC")
                .setDate("date", date).setParameter("exerciseId", exerciseId).list();
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }
}
