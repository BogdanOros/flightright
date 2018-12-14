package io.boros.flightright.image.controller;

import io.boros.flightright.image.ImageLocationProvider;
import io.boros.flightright.utils.LocalProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.boros.flightright.image.controller.ImageLoadingController.LOCAL_IMAGE_API;
import static org.springframework.http.HttpStatus.OK;

@LocalProfile
@RestController
@RequestMapping(LOCAL_IMAGE_API)
@RequiredArgsConstructor
public class ImageLoadingController {

    public static final String LOCAL_IMAGE_API = "images";

    private final ImageLocationProvider urlProvider;

    @GetMapping(path = "/{filename}")
    public ResponseEntity<FileSystemResource> fetchImage(@PathVariable("filename") String filename) {
        return urlProvider.getFile(filename)
                .map(FileSystemResource::new)
                .map(f -> ResponseEntity.status(OK).contentType(MediaType.IMAGE_JPEG).body(f))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
