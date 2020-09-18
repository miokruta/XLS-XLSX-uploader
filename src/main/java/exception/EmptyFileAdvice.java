package exception;

import org.apache.poi.EmptyFileException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EmptyFileAdvice {

    @ResponseBody
    @ExceptionHandler(EmptyFileException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    String emptyFileAdvice(EmptyFileException e) {
        return e.getMessage();
    }
}
