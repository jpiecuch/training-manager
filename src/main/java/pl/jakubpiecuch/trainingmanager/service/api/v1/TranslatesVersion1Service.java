package pl.jakubpiecuch.trainingmanager.service.api.v1;

import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import pl.jakubpiecuch.trainingmanager.web.exception.notfound.NotFoundException;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by jakub on 29.12.2015.
 */
public class TranslatesVersion1Service implements TranslatesService {

    private static final char LIST_DELIMITER = ';';

    private Map<String, List<PropertiesConfiguration>> propertiesConfigurations;
    private List<String> languages;
    private String[] messageSourceFiles;


    @Override
    public List<String> languages() {
        return languages;
    }

    @Override
    public Map<String, String> translates(String language) {
        final List<PropertiesConfiguration> configurations = propertiesConfigurations.get(language);
        if (CollectionUtils.isEmpty(configurations)) {
            throw new NotFoundException();
        }
        Map<String, String> result = new HashMap<>();
        for (final PropertiesConfiguration propertiesConfiguration : configurations) {
            result.putAll(Maps.toMap(propertiesConfiguration.getKeys(), input -> propertiesConfiguration.getString(input)));
        }
        return result;
    }

    public void setLanguages(String[] languages) {
        this.languages = Arrays.asList(languages);
    }

    public void setMessageSourceFiles(String[] messageSourceFiles) {
        this.messageSourceFiles = messageSourceFiles;
    }

    @PostConstruct
    protected void afterPropertiesSet() throws URISyntaxException, ConfigurationException {
        this.propertiesConfigurations = new HashMap<String, List<PropertiesConfiguration>>();
        for (String lang : languages) {
            List<PropertiesConfiguration> configurations = new ArrayList<PropertiesConfiguration>();
            for (String messageSourceFile : messageSourceFiles) {
                PropertiesConfiguration configuration = new PropertiesConfiguration();
                configuration.setListDelimiter(LIST_DELIMITER);
                configuration.setFile(new File(getClass().getResource(String.format(messageSourceFile, lang)).toURI()));
                configuration.load();
                configurations.add(configuration);
            }
            this.propertiesConfigurations.put(lang, configurations);
        }
    }
}
