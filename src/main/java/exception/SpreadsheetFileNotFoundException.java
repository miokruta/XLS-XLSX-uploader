package exception;

public class SpreadsheetFileNotFoundException extends RuntimeException {
    public SpreadsheetFileNotFoundException(Long id) {
        super("Could not find any spreadsheet file with id: " + id);
    }
}
