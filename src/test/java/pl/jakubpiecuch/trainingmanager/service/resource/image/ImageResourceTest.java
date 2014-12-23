package pl.jakubpiecuch.trainingmanager.service.resource.image;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import pl.jakubpiecuch.trainingmanager.web.exception.notfound.NotFoundException;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import static org.junit.Assert.*;

public class ImageResourceTest {

    private static final String FOLDER = "111";
    private static final String EMPTY_FOLDER = "999";
    private static final String IMAGE = "image.jpg";
    private static final String NOT_EXIST_FOLDER = "000";
    private static final String NOT_EXIST_FILE = "notExists.jpg";
    private ImageResource imageResource = new ImageResource();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        folder.newFolder(FOLDER);
        folder.newFile(FOLDER + "/" + IMAGE);
        File file = new File(folder.getRoot().getPath() + "/" + FOLDER + "/" + IMAGE);
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(IMAGE);
        out.close();
        folder.newFolder(EMPTY_FOLDER);
        imageResource.setRoot(folder.getRoot().getPath());
    }

    @Test(expected = NotFoundException.class)
    public void testResourcesEmptyFolder() throws Exception {
        imageResource.resources("/" + EMPTY_FOLDER);
    }

    @Test(expected = NotFoundException.class)
    public void testResourcesFile() throws Exception {
        imageResource.resources("/" + FOLDER + "/" + IMAGE);
    }

    @Test(expected = NotFoundException.class)
    public void testResourcesNotExistFolder() throws Exception {
        imageResource.resources("/" + NOT_EXIST_FOLDER);
    }

    @Test
    public void testResources() throws Exception {
        List<String> resources = imageResource.resources("/" + FOLDER);
        Assert.assertEquals(1, resources.size());
    }

    @Test(expected = NotFoundException.class)
    public void testReadFolder() throws Exception {
        imageResource.read("/" + FOLDER, null);
    }

    @Test(expected = NotFoundException.class)
    public void testReadNotExistFile() throws Exception {
        imageResource.read("/" + NOT_EXIST_FILE, null);
    }

    @Test(expected = NullPointerException.class)
    public void testReadNullOtputStream() throws Exception {
        imageResource.read("/" + FOLDER + "/" + IMAGE, null);
    }

    @Test
    public void testRead() throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageResource.read("/" + FOLDER + "/" + IMAGE, stream);
        Assert.assertEquals(IMAGE, new String(stream.toByteArray()));
    }
}