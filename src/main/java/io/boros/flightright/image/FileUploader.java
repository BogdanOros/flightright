package io.boros.flightright.image;

import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.Optional;

public interface FileUploader {

    Optional<URL> uploadFile(MultipartFile file);

}
