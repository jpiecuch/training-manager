package pl.jakubpiecuch.trainingmanager.service.converter;

import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;

import java.util.List;

/**
 * Created by Rico on 2015-01-18.
 */
public interface Converter<T, E extends CommonEntity> {
    T fromEntity(E entity);
    List<T> fromEntities(List<E> list);
    E toEntity(T object, E entity);
    List<E> toEntities(List<T> list, List<E> entities);
}
