package pl.jakubpiecuch.gymhome.service.resolver.order.description;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class LevelOrderResolverTest extends AbstractOrderResolverTest {

    private LevelOrderResolver resolver = new LevelOrderResolver();

    public LevelOrderResolverTest() {
        super(new HashMap<String, Integer[]>() {
            {
                put(DEFAULT_LANG, new Integer[]{0, 2, 1});
                put(PL_LANG, new Integer[]{0, 1, 2});
            }
        });
    }

    @Override
    public LevelOrderResolver getResolver() {
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