package pl.jakubpiecuch.trainingmanager.service.resource;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface ResourceService {


    ResponseEntity resource(String key) throws IOException;

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
