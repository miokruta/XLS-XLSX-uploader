package exception;

import org.apache.poi.UnsupportedFileFormatException;

import java.util.function.Supplier;

public class UnsupportedMultipartFileFormatException extends UnsupportedFileFormatException{
    public UnsupportedMultipartFileFormatException(String s) {
        super("Unsupported spreadsheet file format: " + s);
    }
}
