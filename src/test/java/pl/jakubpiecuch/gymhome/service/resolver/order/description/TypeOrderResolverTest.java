package pl.jakubpiecuch.gymhome.service.resolver.order.description;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class TypeOrderResolverTest extends AbstractOrderResolverTest {
    private TypeOrderResolver resolver = new TypeOrderResolver();

    public TypeOrderResolverTest() {
        super(new HashMap<String, Integer[]>() {
            {
                put(DEFAULT_LANG, new Integer[]{0, 1, 2, 3, 4, 5, 6});
                put(PL_LANG, new Integer[]{2, 1, 5, 4, 6, 3, 0});
            }
        });
    }

    @Override
    public TypeOrderResolver getResolver() {
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