package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UnsupportedSpreadsheetFileFormatAdvice {

    @ResponseBody
    @ExceptionHandler(UnsupportedMultipartFileFormatException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    String unsupportedSpreadsheetFileFormatAdvice(UnsupportedMultipartFileFormatException e){
        return e.getMessage();

    }
}