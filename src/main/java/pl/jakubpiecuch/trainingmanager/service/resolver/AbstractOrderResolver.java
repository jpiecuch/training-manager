package pl.jakubpiecuch.trainingmanager.service.resolver;

import org.apache.commons.lang3.ArrayUtils;
import pl.jakubpiecuch.trainingmanager.dao.impl.Criteria;

import java.util.Map;

/**
 * Created by Rico on 2015-01-20.
 */
public abstract class AbstractOrderResolver<T extends Enum> implements OrderResolver {
    private String[] langs;
    private String defaultLang;
    private Map<String, Integer[]> orderMap;

    protected abstract T[] values();

    @Override
    public String resolve(String lang, String alias, String property, Criteria.OrderMode mode) {
        Integer[] map = orderMap.get(ArrayUtils.contains(langs, lang) ? lang : defaultLang);
        StringBuilder builder = new StringBuilder(" CASE " + alias + "." + property);
        for (T en : values()) {
            builder.append(" WHEN " + en.ordinal() + " THEN " + map[en.ordinal()]);
        }
        builder.append(" END ").append(mode);
        return builder.toString();
    }


    public void setLangs(String[] langs) {
        this.langs = langs;
    }

    public void setDefaultLang(String defaultLang) {
        this.defaultLang = defaultLang;
    }

    public void setOrderMap(Map<String, Integer[]> orderMap) {
        this.orderMap = orderMap;
    }
}
