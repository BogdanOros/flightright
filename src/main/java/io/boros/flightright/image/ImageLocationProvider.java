package io.boros.flightright.image;

import java.io.File;
import java.net.URL;

public interface ImageLocationProvider {

    URL getURL(String filename);

    File getFile(String filename);

}
