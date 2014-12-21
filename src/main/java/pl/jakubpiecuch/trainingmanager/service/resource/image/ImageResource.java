package pl.jakubpiecuch.trainingmanager.service.resource.image;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Required;
import pl.jakubpiecuch.trainingmanager.service.resource.ResourceService;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class ImageResource implements ResourceService {

    private String root;

    @Override
    public List<String> resources(String handler) {
        File directory = new File(root + handler);
        return directory.exists() && directory.isDirectory() ? Arrays.asList(directory.list()) : null;
    }

    @Override
    public boolean read(String handler, OutputStream stream) {
        File file = new File(root + handler);
        if (file.exists()) {
            InputStream is = null;
            try {
                is = new FileInputStream(file);
                IOUtils.copy(is, stream);
            } catch (IOException ex) {
            } finally {
                IOUtils.closeQuietly(is);
            }
        }
        return file.exists();
    }

    @Required
    public void setRoot(String root) {
        this.root = root;
    }

    @PostConstruct
    protected void afterPropertiesSet() {
        File directory = new File(root);
        if (!directory.exists()) {
            try {
                FileUtils.forceMkdir(directory);
            } catch (IOException ex) {
            }
        }
    }
}
