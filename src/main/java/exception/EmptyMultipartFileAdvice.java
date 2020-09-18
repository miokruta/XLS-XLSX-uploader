package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EmptyMultipartFileAdvice {

    @ResponseBody
    @ExceptionHandler(EmptyMultipartFileException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    String EmptyMultipartFileAdvice(EmptyMultipartFileException e) {
        return e.getMessage();
    }
}
