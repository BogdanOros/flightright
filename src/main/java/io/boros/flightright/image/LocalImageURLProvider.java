package io.boros.flightright.image;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.String.format;

@ConditionalOnBean(ImageLoadingController.class)
@Service
public class LocalImageURLProvider implements ImageFileProvider {

    private final String path = "images";
    private final int port;

    public LocalImageURLProvider(@Value("${server.port}") int port) {
        this.port = port;
    }

    @Override
    public URL getURL(String filename) {
        try {
            return new URL(format("http://localhost:%d/%s/%s", port, path, filename));
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found");
        }
    }

    @Override
    public File getFile(String filename) {
        return new File(path + "/" + filename);
    }
}
