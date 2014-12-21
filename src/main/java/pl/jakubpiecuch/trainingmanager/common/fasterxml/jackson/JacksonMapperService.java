package pl.jakubpiecuch.trainingmanager.common.fasterxml.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.jakubpiecuch.trainingmanager.common.MapperService;

import javax.annotation.PostConstruct;
import java.io.InputStream;

/**
 * Created by Rico on 2014-11-22.
 */
public class JacksonMapperService implements MapperService {
    private final static Logger log = LoggerFactory.getLogger(JacksonMapperService.class);
    private ObjectMapper mapper;

    @Override
    public <T> T getObject(InputStream stream, Class<T> clazz) throws Exception {
        return mapper.readValue(stream, clazz);
    }


    @PostConstruct
    private void afterPropertiesSet() {
        mapper = new ObjectMapper();
    }
}
