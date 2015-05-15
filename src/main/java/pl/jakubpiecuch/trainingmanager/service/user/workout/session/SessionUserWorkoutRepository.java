package pl.jakubpiecuch.trainingmanager.service.user.workout.session;

import com.google.common.collect.Lists;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import pl.jakubpiecuch.trainingmanager.dao.PageResult;
import pl.jakubpiecuch.trainingmanager.domain.UserWorkout;
import pl.jakubpiecuch.trainingmanager.service.converter.Converter;
import pl.jakubpiecuch.trainingmanager.service.repository.AbstractRepository;
import pl.jakubpiecuch.trainingmanager.service.user.workout.UserWorkoutDto;

import java.util.List;

/**
 * Created by Rico on 2015-01-18.
 */
public class SessionUserWorkoutRepository extends AbstractRepository<UserWorkoutDto, UserWorkoutCriteria> {

    private Converter<UserWorkoutDto, UserWorkout> converter;

    @Override
    @Transactional
    public PageResult<UserWorkoutDto> read(UserWorkoutCriteria criteria) {
        final PageResult<UserWorkout> result = dao.findByCriteria(criteria);
        final List list = Lists.newArrayList(converter.fromEntities(result.getResult()));

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

    @Override
    public void update(UserWorkoutDto element) {
        UserWorkout entity = converter.toEntity(element, (UserWorkout) dao.findById(element.getId()));
        validator.validate(element, new BeanPropertyBindingResult(element, name));
        dao.update(entity);
    }

    @Override
    public long create(UserWorkoutDto element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }

}
