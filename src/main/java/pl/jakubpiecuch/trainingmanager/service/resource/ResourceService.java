package pl.jakubpiecuch.trainingmanager.service.resource;

import org.springframework.http.MediaType;

import java.io.OutputStream;
import java.util.List;

public interface ResourceService {
    
    public enum Type { 
        image("jpg", "png");
        
        public String[] extensions;
        
        private Type(String... extensions) {
            this.extensions = extensions;
        }
    }
    
    List<String> resources(String handler);
    byte[] read(String handler) throws Exception;
    MediaType getMediaType(String handler) throws Exception;
    boolean isCatalog(String handler);
}
