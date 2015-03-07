package pl.jakubpiecuch.trainingmanager.org.hibernate.dialect;

import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.Type;

import java.util.List;

/**
 * Created by Rico on 2015-03-07.
 */
public abstract class AbstractOver implements SQLFunction {
    @Override
    public boolean hasArguments() {
        return true;
    }

    @Override
    public boolean hasParenthesesIfNoArguments() {
        return false;
    }

    @Override
    public Type getReturnType(Type firstArgumentType, Mapping mapping) {
        return firstArgumentType;
    }

    @Override
    public abstract String render(Type firstArgumentType, List arguments, SessionFactoryImplementor factory);
}
