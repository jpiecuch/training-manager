package pl.jakubpiecuch.trainingmanager.service.flow;

import pl.jakubpiecuch.trainingmanager.domain.CommonEntity;
import pl.jakubpiecuch.trainingmanager.service.converter.Converter;

import java.util.List;

/**
 * Created by Rico on 2014-12-31.
 */
public interface FlowConverter<T extends Flow, E extends CommonEntity> extends Converter<T, E> {
}
