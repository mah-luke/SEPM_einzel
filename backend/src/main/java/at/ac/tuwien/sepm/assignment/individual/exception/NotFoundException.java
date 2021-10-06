package at.ac.tuwien.sepm.assignment.individual.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends LoggedRuntimeException {
    public NotFoundException() { super(); }
    public NotFoundException(String message) { super(message); }
    public NotFoundException(Throwable cause) { super(cause); }
    public NotFoundException(String message, Throwable cause) { super(message, cause); }

}
