package pl.jakubpiecuch.gymhome.service.converter;

import java.util.List;

/**
 * Created by Rico on 2015-01-18.
 */
public interface Converter<T, E> {
    T fromEntity(E entity);

    List<T> fromEntities(List<E> list);

    E toEntity(T object, E entity);
}
