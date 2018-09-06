package pl.jakubpiecuch.gymhome.service.api.v1;

import pl.jakubpiecuch.gymhome.web.exception.notfound.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by jakub on 29.12.2015.
 */
public class TranslatesVersion1Service implements TranslatesService {

    private Map<String, Properties> properties;
    private List<String> languages;


    @Override
    public List<String> languages() {
        return languages;
    }

    @Override
    public Map<String, String> translates(String language) {
        Properties property = properties.get(language);
        if (property == null) {
            throw new NotFoundException();
        }
        Map<String, String> result = new HashMap<>();
        property.entrySet().forEach(objectObjectEntry -> result.put(objectObjectEntry.getKey().toString(), objectObjectEntry.getValue().toString()));
        return result;
    }

    public void setProperties(Map<String, Properties> properties) {
        this.properties = properties;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }
}
