package at.ac.tuwien.sepm.assignment.individual.exception;

public class PersistenceException extends Exception {
    public PersistenceException() { super(); }
    public PersistenceException(String message) { super(message); }
    public PersistenceException(Throwable cause) { super(cause); }
    public PersistenceException(String message, Throwable cause) { super(message, cause); }
}
