package pl.jakubpiecuch.trainingmanager.service.converter;

import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;

import java.util.List;

/**
 * Created by Rico on 2015-01-18.
 */
public interface Converter<T, E extends CommonEntity> {
    T fromEntity(E entity, boolean full);
    List<T> fromEntityList(List<E> list, boolean full);
    E toEntity(T object);
    List<E> toEntityList(List<T> list);
}
