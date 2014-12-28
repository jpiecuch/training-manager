package pl.jakubpiecuch.trainingmanager.org.hibernate.dialect;

import org.hibernate.QueryException;
import org.hibernate.dialect.PostgreSQL82Dialect;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.Type;

import java.util.List;


public class PostgreSQLDialect extends PostgreSQL82Dialect {

    public final static String OVER = "over";
    protected final static String PARTITION = "partition";
    public final static String OVER_FORMAT = "%s " + OVER + "(%s)";

    public PostgreSQLDialect() {
        super();
        registerTypesAndFunctions();
    }

    protected void registerTypesAndFunctions() {
        registerFunction(OVER, new Over());
        registerFunction(PARTITION, new StandardSQLFunction(PARTITION));
    }

    public static class Over implements SQLFunction {
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
            return String.format(OVER_FORMAT, arguments.get(0), arguments.size() == 2 ? arguments.get(1) : "");
        }
    }
}
