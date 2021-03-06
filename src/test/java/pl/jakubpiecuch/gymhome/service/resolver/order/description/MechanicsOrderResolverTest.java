package pl.jakubpiecuch.gymhome.service.resolver.order.description;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class MechanicsOrderResolverTest extends AbstractOrderResolverTest {

    private MechanicsOrderResolver resolver = new MechanicsOrderResolver();

    public MechanicsOrderResolverTest() {
        super(new HashMap<String, Integer[]>() {
            {
                put(DEFAULT_LANG, new Integer[]{0, 1});
                put(PL_LANG, new Integer[]{1, 0});
            }
        });
    }

    @Override
    public MechanicsOrderResolver getResolver() {
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