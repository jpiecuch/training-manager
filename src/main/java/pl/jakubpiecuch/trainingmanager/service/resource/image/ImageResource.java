package pl.jakubpiecuch.trainingmanager.service.resource.image;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.MediaType;
import pl.jakubpiecuch.trainingmanager.service.resource.ResourceService;
import pl.jakubpiecuch.trainingmanager.web.exception.notfound.NotFoundException;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageResource implements ResourceService {

    private String root;

    @Override
    public MediaType getMediaType(String handler) throws IOException {
        File file = new File(root + handler);
        if (!file.exists() || file.isDirectory()) {
            throw new NotFoundException();
        }
        return MediaType.valueOf(Files.probeContentType(Paths.get(root + handler))) ;
    }

    @Override
    public boolean isCatalog(String handler) {
        File file = new File(root + handler);
        if (!file.exists()) {
            throw new NotFoundException();
        }
        return file.isDirectory();
    }

    @Override
    public List<String> resources(final String handler) {
        final File directory = new File(root + handler);
        if (!directory.exists() || !directory.isDirectory() || directory.list().length == 0) {
            throw new NotFoundException();
        }
        return Lists.transform(Arrays.asList(directory.list()), new Function<String, String>() {
            @Override
            public String apply(String input) {
                return handler + "/" + input;
            }
        });
    }

    @Override
    public byte[] read(String handler) throws IOException {
        File file = new File(root + handler);
        if (!file.exists() || file.isDirectory()) {
            throw new NotFoundException();
        }
        return FileUtils.readFileToByteArray(file);
    }

    @Required
    public void setRoot(String root) {
        this.root = root;
    }

    @PostConstruct
    protected void afterPropertiesSet() throws IOException {
        File directory = new File(root);
        if (!directory.exists()) {
            FileUtils.forceMkdir(directory);
        }
    }
}
