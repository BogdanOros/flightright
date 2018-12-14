package io.boros.flightright.image;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface FileUploader {

    Optional<String> uploadFile(MultipartFile file);

}
