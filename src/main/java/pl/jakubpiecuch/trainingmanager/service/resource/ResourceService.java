package pl.jakubpiecuch.trainingmanager.service.resource;

import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.List;

public interface ResourceService {

    List<String> resources(String handler);

    byte[] read(String handler) throws IOException;

    MediaType getMediaType(String handler) throws IOException;

    boolean isCatalog(String handler);

    enum Type {
        image("jpg", "png");

        private String[] extensions;

        Type(String... extensions) {
            this.extensions = extensions;
        }

        public String[] getExtensions() {
            return extensions;
        }
    }
}
