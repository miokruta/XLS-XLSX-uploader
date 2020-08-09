package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SpreadsheetFileNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(SpreadsheetFileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String SpreadsheetFileNotFoundAdvice(SpreadsheetFileNotFoundException e){
        return e.getMessage();
    }
}