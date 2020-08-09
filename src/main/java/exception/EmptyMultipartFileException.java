package exception;

import org.springframework.web.multipart.MultipartException;

public class EmptyMultipartFileException extends MultipartException {
    public EmptyMultipartFileException(String msg) {
        super(msg);
    }
}
