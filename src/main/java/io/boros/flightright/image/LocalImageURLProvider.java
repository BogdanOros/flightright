package io.boros.flightright.image;

import io.boros.flightright.utils.LocalProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.String.format;

@LocalProfile
@Service
public class LocalImageURLProvider implements ImageLocationProvider {

    private final String path = ImageLoadingController.LOCAL_IMAGE_API;
    private final int port;

    public LocalImageURLProvider(@Value("${server.port}") int port) {
        this.port = port;
    }

    @Override
    public URL getURL(String filename) {
        try {
            return new URL(format("http://localhost:%d/%s/%s", port, path, filename));
        } catch (MalformedURLException e) {
            throw new RuntimeException("Cannot create URL for file:  " + filename);
        }
    }

    @Override
    public File getFile(String filename) {
        return new File(path + "/" + filename);
    }
}
