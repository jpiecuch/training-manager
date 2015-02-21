package pl.jakubpiecuch.trainingmanager.service.user.workout.session;

import org.springframework.transaction.annotation.Transactional;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.dao.RepoDao;
import pl.jakubpiecuch.trainingmanager.dao.UserWorkoutDao;
import pl.jakubpiecuch.trainingmanager.domain.UserWorkout;
import pl.jakubpiecuch.trainingmanager.service.converter.Converter;
import pl.jakubpiecuch.trainingmanager.service.user.workout.UserWorkoutDto;
import pl.jakubpiecuch.trainingmanager.service.user.workout.UserWorkoutManager;

import java.util.List;

/**
 * Created by Rico on 2015-01-18.
 */
public class SessionUserWorkoutManager implements UserWorkoutManager {

    UserWorkoutDao dao;
    Converter converter;

    @Override
    @Transactional
    public PageResult<UserWorkoutDto> read(UserWorkoutCriteria criteria) {
        RepoDao repoDao = (RepoDao) dao;
        final PageResult<UserWorkout> result = repoDao.findByCriteria(criteria);
        final List list = converter.fromEntityList(result.getResult(), true);

        return new PageResult<UserWorkoutDto>() {
            @Override
            public List<UserWorkoutDto> getResult() {
                return list;
            }

            @Override
            public long getCount() {
                return result.getCount();
            }
        };
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }

    public void setDao(UserWorkoutDao dao) {
        this.dao = dao;
    }
}
