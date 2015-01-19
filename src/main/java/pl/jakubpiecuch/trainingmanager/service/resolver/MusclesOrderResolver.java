package pl.jakubpiecuch.trainingmanager.service.resolver;

import org.apache.commons.lang.ArrayUtils;
import pl.jakubpiecuch.trainingmanager.domain.Description;
import pl.jakubpiecuch.trainingmanager.service.OrderResolver;
import pl.jakubpiecuch.trainingmanager.service.repository.Criteria;

import java.util.Map;

/**
 * Created by Rico on 2015-01-19.
 */
public class MusclesOrderResolver implements OrderResolver<Description.Muscles> {

    String[] langs;
    String defaultLang;
    Map<String, Map<Description.Muscles, Integer>> orderMap;

    @Override
    public String resolve(String lang, String alias, String property, Criteria.OrderMode mode) {
        Map<Description.Muscles, Integer> map = orderMap.get(ArrayUtils.contains(langs, lang) ? lang : defaultLang);
        StringBuilder builder = new StringBuilder(" CASE " + alias + "." + property + " ");
        for(Map.Entry<Description.Muscles, Integer> entry : map.entrySet()) {
            builder.append(" WHEN " + entry.getKey().ordinal() + " THEN " + entry.getValue());
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

    public void setOrderMap(Map<String, Map<Description.Muscles, Integer>> orderMap) {
        this.orderMap = orderMap;
    }
}
