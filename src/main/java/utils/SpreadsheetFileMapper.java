package utils;

import domain.SpreadsheetFile;
import org.springframework.data.annotation.Immutable;
import org.springframework.web.multipart.MultipartFile;

/**
 * This class consists of the only {@code static} utility method {@code mapMultipartFile}.
 */
@Immutable
public final class SpreadsheetFileMapper {
    private SpreadsheetFileMapper() {
            throw new AssertionError();
    }

    /**
     * This method takes as parameter file from POST request.
     * With help of {@link utils.MultipartFileValidator} maps this file to domain model {@link domain.SpreadsheetFile} and returns it.
     * It also sets new object's id as -1 value as default.
     * @param file {@link MultipartFile}
     * @return domain model {@link domain.SpreadsheetFile}
     */
    public static SpreadsheetFile mapMultipartFile(MultipartFile file) {
        MultipartFileValidator multipartFileValidator = new MultipartFileValidator(file);

        return new SpreadsheetFile(
                -1,
                multipartFileValidator.getFileName(),
                multipartFileValidator.getFileContent(),
                multipartFileValidator.getFileExtension()
        );
    }

}
