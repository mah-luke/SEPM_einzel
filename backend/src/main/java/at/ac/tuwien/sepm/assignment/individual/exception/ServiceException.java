package at.ac.tuwien.sepm.assignment.individual.exception;

public class ServiceException extends Exception {
    public ServiceException() { super(); }
    public ServiceException(String message) { super(message); }
    public ServiceException(Throwable cause) { super(cause); }
    public ServiceException(String message, Throwable cause) { super(message, cause); }
}
