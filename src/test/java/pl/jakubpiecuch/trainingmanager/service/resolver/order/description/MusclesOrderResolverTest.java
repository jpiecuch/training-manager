package pl.jakubpiecuch.trainingmanager.service.resolver.order.description;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class MusclesOrderResolverTest extends AbstractOrderResolverTest{
    public MusclesOrderResolverTest() {
        super(new HashMap<String, Integer[]>() {
            {
                put(DEFAULT_LANG, new Integer[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15});
                put(PL_LANG, new Integer[]{9,2,1,7,6,11,10,12,8,5,13,14,12,0,3,15});
            }
        });
    }

    private MusclesOrderResolver resolver = new MusclesOrderResolver();

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