package pl.jakubpiecuch.gymhome.service.resolver.order.description;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class MusclesOrderResolverTest extends AbstractOrderResolverTest {
    private MusclesOrderResolver resolver = new MusclesOrderResolver();

    public MusclesOrderResolverTest() {
        super(new HashMap<String, Integer[]>() {
            {
                put(DEFAULT_LANG, new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11});
                put(PL_LANG,      new Integer[]{9, 1, 0, 10, 8, 3, 2, 7, 6, 5, 4, 11});
            }
        });
    }

    @Override
    public MusclesOrderResolver getResolver() {
        return resolver;
    }

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testResolve() {
        assertOrder();
    }
}