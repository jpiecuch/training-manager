package pl.jakubpiecuch.trainingmanager.orm.hibernate.dialect;

import org.junit.Test;
import pl.jakubpiecuch.trainingmanager.org.hibernate.dialect.PostgreSQLDialect;
import pl.jakubpiecuch.trainingmanager.org.hibernate.dialect.PostgreSQLDialect.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class OverTest {

    private static final String FIRST_EL = "first";
    private static final String SECOND_EL = "second";
    private static final List<String> LIST = new ArrayList<String>() {
        {
            add(FIRST_EL);
            add(SECOND_EL);
        }
    };
    private static final List<String> ONE_EL_LIST = new ArrayList<String>() {
        {
            add(FIRST_EL);
        }
    };
    private Over over = new Over();

    @Test
    public void testRender() throws Exception {
        assertEquals(String.format(PostgreSQLDialect.OVER_FORMAT, FIRST_EL, SECOND_EL), over.render(null, LIST, null));

        assertEquals(String.format(PostgreSQLDialect.OVER_FORMAT, FIRST_EL, ""), over.render(null, ONE_EL_LIST, null));
    }


    @Test(expected = NullPointerException.class)
    public void testRenderNullList() throws Exception {
        over.render(null, null, null);
    }
}