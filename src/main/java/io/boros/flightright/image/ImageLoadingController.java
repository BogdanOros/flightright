package io.boros.flightright.image;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Profile("local")
@RestController
@RequestMapping("/images")
@RequiredArgsConstructor
public class ImageLoadingController {

    private final ImageFileProvider urlProvider;

    @GetMapping(path = "/{filename}")
    public ResponseEntity<FileSystemResource> fetchImage(@PathVariable("filename") String filename) {
        FileSystemResource image = new FileSystemResource(urlProvider.getFile(filename));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }

}
