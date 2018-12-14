package io.boros.flightright.image;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@ConditionalOnBean(ImageLoadingController.class)
@Component
public class LocalFileUploader implements FileUploader {

    private final ImageFileProvider imageFileProvider;

    public LocalFileUploader(ImageFileProvider imageFileProvider) {
        this.imageFileProvider = imageFileProvider;
        try {
            Path dir = Paths.get("images");
            if (!Files.exists(dir)) {
                Files.createDirectory(dir);
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot create folder to store images locally");
        }
    }

    @Override
    public Optional<URL> uploadFile(MultipartFile file) {
        try {
            String filename = generateFilename(file);
            Files.write(Paths.get(new File("images/" + filename).toURI()), file.getBytes());
            return Optional.of(this.imageFileProvider.getURL(filename));
        } catch (IOException ex) {
            return Optional.empty();
        }
    }

    private String generateFilename(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        return UUID.randomUUID().toString() + extension;
    }

}
