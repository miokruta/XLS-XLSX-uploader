package utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

public final class PostFileHandler {
    private String fileName;
    private byte[] fileContent;
    private String extension;

    private PostFileHandler() {}

    public PostFileHandler(MultipartFile file) {
        try {
            separateOriginalName(Objects.requireNonNull(file.getOriginalFilename()));
            this.fileContent = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getFileName() {
        return this.fileName;
    }

    public byte[] getFileContent() {
        return this.fileContent;
    }

    public String getExtension() {
        return this.extension;
    }

    private void separateOriginalName(String originalFileName) {
        String[] parts = originalFileName.split("\\.", 1);
        this.fileName = parts[0];
        this.extension = parts[1];
    }

}
