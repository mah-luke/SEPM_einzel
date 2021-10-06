package at.ac.tuwien.sepm.assignment.individual.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalArgumentException extends LoggedRuntimeException {
    public IllegalArgumentException() { super(); }
    public IllegalArgumentException(String message) { super(message); }
    public IllegalArgumentException(Throwable cause) { super(cause); }
    public IllegalArgumentException(String message, Throwable cause) { super(message, cause); }
}
