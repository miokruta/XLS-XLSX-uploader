package exception;

import org.apache.poi.UnsupportedFileFormatException;

public class UnsupportedSpreadsheetFileFormatException extends UnsupportedFileFormatException {
    public UnsupportedSpreadsheetFileFormatException(String s) {
        super("Unsupported spreadsheet file format: " + s);
    }
}
