package pl.jakubpiecuch.trainingmanager.orm.hibernate.dialect;

import org.hibernate.dialect.PostgreSQL82Dialect;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.QueryException;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.Type;

import java.util.List;


public class PostgreSQLDialect extends PostgreSQL82Dialect {

    public PostgreSQLDialect() {
        super();
        registerTypesAndFunctions();
    }

    protected void registerTypesAndFunctions() {
        registerFunction("over", new Over());
        registerFunction("partition", new StandardSQLFunction("partition"));
    }

    private class Over implements SQLFunction {
        @Override
        public boolean hasArguments() {
            return true;
        }

        @Override
        public boolean hasParenthesesIfNoArguments() {
            return false;
        }

        @Override
        public Type getReturnType(Type firstArgumentType, Mapping mapping) throws QueryException {
            return firstArgumentType;
        }

        @Override
        public String render(Type firstArgumentType, List arguments, SessionFactoryImplementor factory) throws QueryException {
            StringBuffer buf = new StringBuffer(arguments.get(0).toString());
            buf.append(" over(");
            if (arguments.size() == 2) {
                buf.append(arguments.get(1));
            }
            buf.append(")");
            return buf.toString();
        }
    }
}
