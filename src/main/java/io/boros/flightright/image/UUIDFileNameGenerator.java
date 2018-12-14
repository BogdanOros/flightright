package io.boros.flightright.image;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UUIDFileNameGenerator implements FileNameGenerator {

    @Override
    public String generate(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        if (originalFilename == null) {
            return uuid;
        }

        int extensionStart = originalFilename.lastIndexOf(".");
        boolean noExtensionPresent = extensionStart == -1;
        if (noExtensionPresent) {
            return uuid;
        }
        String extension = originalFilename.substring(extensionStart);
        return uuid + extension;
    }
}
