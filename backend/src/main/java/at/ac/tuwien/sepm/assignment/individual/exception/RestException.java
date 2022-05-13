package at.ac.tuwien.sepm.assignment.individual.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class RestException extends Exception {
    public RestException() { super(); }
    public RestException(String message) { super(message); }
    public RestException(Throwable cause) { super(cause); }
    public RestException(String message, Throwable cause) { super(message, cause); }
}
