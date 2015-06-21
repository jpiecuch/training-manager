package pl.jakubpiecuch.trainingmanager.org.hibernate.dialect;

import org.hibernate.dialect.PostgreSQL82Dialect;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.Type;

import java.util.List;


public class PostgreSQLDialect extends PostgreSQL82Dialect {

    public static final String OVER = "over";
    public static final String OVER_FORMAT = "%s " + OVER + "(%s)";

    public PostgreSQLDialect() {
        super();
        registerTypesAndFunctions();
    }

    protected void registerTypesAndFunctions() {
        registerFunction(OVER, new Over());
    }

    public static class Over extends AbstractOver {
        @Override
        public String render(Type firstArgumentType, List arguments, SessionFactoryImplementor factory) {
            return String.format(OVER_FORMAT, arguments.get(0), arguments.size() == 2 ? arguments.get(1) : "");
        }
    }
}
