package io.boros.flightright.image;

import io.boros.flightright.utils.LocalProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@LocalProfile
@Component
public class LocalFileUploader implements FileUploader {

    private static final Logger log = LoggerFactory.getLogger(LocalFileUploader.class);

    private final FileNameGenerator fileNameGenerator;

    public LocalFileUploader(FileNameGenerator fileNameGenerator) {
        this.fileNameGenerator = fileNameGenerator;
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
    public Optional<String> uploadFile(MultipartFile file) {
        try {
            String filename = fileNameGenerator.generate(file.getOriginalFilename());
            file.transferTo(Files.createFile(Paths.get("images/" + filename)));
            return Optional.of(filename);
        } catch (IOException ex) {
            log.warn("Failed to persist image locally: " + file.getOriginalFilename());
            return Optional.empty();
        }
    }
}
