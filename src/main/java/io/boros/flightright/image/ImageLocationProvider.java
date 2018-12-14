package io.boros.flightright.image;

import java.io.File;
import java.net.URL;
import java.util.Optional;

public interface ImageLocationProvider {

    Optional<URL> getURL(String filename);

    Optional<File> getFile(String filename);

}
