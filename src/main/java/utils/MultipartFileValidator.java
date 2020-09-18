package utils;

import enums.FileExtensions;
import exception.UnsupportedMultipartFileFormatException;
import org.apache.poi.EmptyFileException;
import org.springframework.data.annotation.Immutable;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Objects;

/**
 * <h1>The {@link utils.MultipartFileValidator} class </h1>
 * <p>
 * is responsible for controlling the validation of imported multipart file during POST request
 * before mapping it to representational domain object.
 * Immutable
 * Threadsafe
 *
 * @author creazy
 * @version 1.0
 * @since 2020-09-15
 */

@Immutable
public final class MultipartFileValidator {
    private final MultipartFile file;
    private final String fileName;
    private final FileExtensions fileExtension;

    private MultipartFileValidator() {
        throw new AssertionError();
    }

    /**
     * The only constructor which performs whole file validation using {@link MultipartFile} and {@link java.util.Objects} methods.
     * Throws {@link EmptyFileException} on empty file content or empty file name.
     * Throws {@link UnsupportedMultipartFileFormatException} on file having unsupported format
     * @param file {@link org.springframework.web.multipart.MultipartFile}
     * @exception EmptyFileException
     */
    public MultipartFileValidator(MultipartFile file) {
        if (file.isEmpty() || Objects.isNull(file.getOriginalFilename())) {
            throw new EmptyFileException();
        } else {
            String[] originalFileName = file.getOriginalFilename().split("\\.");
            for (String str: originalFileName) {
                System.out.println(str+" ");
            }
            this.fileName = originalFileName[0];

            try {
                this.fileExtension = FileExtensions.valueOf(originalFileName[1].toUpperCase());
                this.file = file;
            } catch (IllegalArgumentException e) {
                //exception translation
                throw new UnsupportedMultipartFileFormatException(originalFileName[1]);
            }
        }
    }

    /**
     * This method returns whole string before dot mark in original {@link MultipartFile} name.
     *
     * @return the file name
     */
    String getFileName() {
        return this.fileName;
    }

    /**
     * This method returns enum represents file format.
     *
     * @return enum file extension
     */
    FileExtensions getFileExtension() {
        return this.fileExtension;
    }

    /**
     * This method returns defensive copy of file content
     *
     * @return the byte array
     */
    byte[] getFileContent() {
        try {
            //defensive copy
            return this.file.getBytes().clone();
        } catch (IOException e) {
            //exception translation
            throw new EmptyFileException();
        }
    }
}
