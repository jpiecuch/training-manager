package pl.jakubpiecuch.trainingmanager.service.resource;

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
    boolean read(String handler, OutputStream stream);
}
