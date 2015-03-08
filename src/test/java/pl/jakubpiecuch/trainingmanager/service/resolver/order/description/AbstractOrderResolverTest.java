package pl.jakubpiecuch.trainingmanager.service.resolver.order.description;

import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;
import pl.jakubpiecuch.trainingmanager.service.resolver.AbstractOrderResolver;
import pl.jakubpiecuch.trainingmanager.service.resolver.OrderResolver;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Rico on 2015-03-08.
 */
public abstract class AbstractOrderResolverTest {
    protected static final String DEFAULT_LANG = "en";
    protected static final String PL_LANG = "pl";
    protected static final String[] LANGS = new String[] {DEFAULT_LANG, PL_LANG};
    protected static final String ALIAS = "entity";
    protected static final String PROPERTY = "property";
    protected static final Criteria.OrderMode ORDER_MODE = Criteria.OrderMode.ASC;
    protected final Map<String, Integer[]> ORDER_MAP;

    protected  AbstractOrderResolverTest(Map<String, Integer[]> orderMap) {
        ORDER_MAP = orderMap;
    }

    protected abstract AbstractOrderResolver getResolver();

    protected void setUp() {
        getResolver().setDefaultLang(DEFAULT_LANG);
        getResolver().setLangs(LANGS);
        getResolver().setOrderMap(ORDER_MAP);
    }

    protected void assertOrder() {
        String orderQuery = getResolver().resolve(PL_LANG, ALIAS, PROPERTY, ORDER_MODE);
        StringBuilder expectedOrder = new StringBuilder();
        for (int i =0; i < ORDER_MAP.get(PL_LANG).length; i++) {
            expectedOrder.append(" WHEN " + i + " THEN " + ORDER_MAP.get(PL_LANG)[i]);
        }
        assertNotNull(orderQuery);
        assertEquals(" CASE " + ALIAS + "." + PROPERTY + expectedOrder.toString() + " END " + ORDER_MODE, orderQuery);
    }
}
