package at.ac.tuwien.sepm.assignment.individual.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class StateConflictException extends RuntimeException {
    public StateConflictException() { super(); }
    public StateConflictException(String message) { super(message); }
    public StateConflictException(Throwable cause) { super(cause); }
    public StateConflictException(String message, Throwable cause) { super(message, cause); }
}
