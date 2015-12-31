package pl.jakubpiecuch.trainingmanager.service.resource.image;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import pl.jakubpiecuch.trainingmanager.service.crypt.CryptService;
import pl.jakubpiecuch.trainingmanager.service.resource.ResourceService;
import pl.jakubpiecuch.trainingmanager.web.exception.notfound.NotFoundException;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class ImageResource implements ResourceService {

    private String root;
    private CryptService cryptService;

    @Override
    public ResponseEntity resource(String key) throws IOException {
        final String handler = cryptService.decrypt(key, null);
        HttpHeaders headers = new HttpHeaders();
        Object body;
        if (isCatalog(handler)) {
            body = Lists.transform(resources(handler), input -> cryptService.encrypt(input));
        } else {
            headers.setContentType(getMediaType(handler));
            body = read(handler);
        }
        return new ResponseEntity(body, headers, HttpStatus.OK);
    }

    public MediaType getMediaType(String handler) throws IOException {
        File file = new File(root + handler);
        if (!file.exists() || file.isDirectory()) {
            throw new NotFoundException();
        }
        return MediaType.valueOf(Files.probeContentType(Paths.get(root + handler)));
    }

    public boolean isCatalog(String handler) {
        File file = new File(root + handler);
        if (!file.exists()) {
            throw new NotFoundException();
        }
        return file.isDirectory();
    }

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

    public void setCryptService(CryptService cryptService) {
        this.cryptService = cryptService;
    }

    @PostConstruct
    protected void afterPropertiesSet() throws IOException {
        File directory = new File(root);
        if (!directory.exists()) {
            FileUtils.forceMkdir(directory);
        }
    }
}
