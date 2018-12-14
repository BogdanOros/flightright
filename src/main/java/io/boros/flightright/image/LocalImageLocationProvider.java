package io.boros.flightright.image;

import io.boros.flightright.image.controller.ImageLoadingController;
import io.boros.flightright.utils.LocalProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static java.lang.String.format;

@LocalProfile
@Service
public class LocalImageLocationProvider implements ImageLocationProvider {

    private final String path = ImageLoadingController.LOCAL_IMAGE_API;
    private final int port;

    public LocalImageLocationProvider(@Value("${server.port}") int port) {
        this.port = port;
    }

    @Override
    public Optional<URL> getURL(String filename) {
        try {
            return Optional.of(new URL(format("http://localhost:%d/%s/%s", port, path, filename)));
        } catch (MalformedURLException e) {
            throw new RuntimeException("Cannot create URL for file:  " + filename);
        }
    }

    @Override
    public Optional<File> getFile(String filename) {
        // Naive, should be validated correctly
        if (filename.contains("..")) {
            return Optional.empty();
        }
        Path path = Paths.get(this.path, filename);
        if (Files.exists(path)) {
            return Optional.of(path.toFile());
        }
        return Optional.empty();
    }
}
