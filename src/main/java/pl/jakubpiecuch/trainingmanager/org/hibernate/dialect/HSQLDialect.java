package pl.jakubpiecuch.trainingmanager.org.hibernate.dialect;

import org.hibernate.QueryException;
import org.hibernate.dialect.PostgreSQL82Dialect;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.Type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HSQLDialect extends PostgreSQL82Dialect {

    public final static String OVER = "over";
    private final static String COUNT = "count(*)";
    private static Map<String, String> REPLACE_MAP = new HashMap<String, String>();
    public final static String OVER_FORMAT = "%s " + OVER + "(%s)";

    public HSQLDialect() {
        super();
        registerTypesAndFunctions();
        REPLACE_MAP.put(COUNT, "ROW_NUMBER()");
    }

    protected void registerTypesAndFunctions() {
        registerFunction(OVER, new Over());
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
            return String.format(OVER_FORMAT, REPLACE_MAP.get(arguments.get(0)), arguments.size() == 2 ? arguments.get(1) : "");
        }
    }
}
