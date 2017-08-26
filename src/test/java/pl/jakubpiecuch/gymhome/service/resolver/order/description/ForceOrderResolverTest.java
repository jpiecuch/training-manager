package pl.jakubpiecuch.gymhome.service.resolver.order.description;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class ForceOrderResolverTest extends AbstractOrderResolverTest {

    private ForceOrderResolver resolver = new ForceOrderResolver();

    public ForceOrderResolverTest() {
        super(new HashMap<String, Integer[]>() {
            {
                put(DEFAULT_LANG, new Integer[]{0, 1, 2});
                put(PL_LANG, new Integer[]{1, 0, 2});
            }
        });
    }

    @Override
    public ForceOrderResolver getResolver() {
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