package pl.jakubpiecuch.trainingmanager.service.resource.image;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Required;
import pl.jakubpiecuch.trainingmanager.service.resource.ResourceService;
import pl.jakubpiecuch.trainingmanager.web.exception.notfound.NotFoundException;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.Arrays;
import java.util.List;

public class ImageResource implements ResourceService {

    private String root;



    @Override
    public List<String> resources(String handler) {
        File directory = new File(root + handler);
        if (!directory.exists() || !directory.isDirectory() || directory.list().length == 0) {
            throw new NotFoundException();
        }
        return Arrays.asList(directory.list());
    }

    @Override
    public void read(String handler, OutputStream stream) throws Exception {
        File file = new File(root + handler);
        if (!file.exists() || file.isDirectory()) {
            throw new NotFoundException();
        }
        InputStream is = new FileInputStream(file);
        IOUtils.copy(is, stream);
        IOUtils.closeQuietly(is);
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
