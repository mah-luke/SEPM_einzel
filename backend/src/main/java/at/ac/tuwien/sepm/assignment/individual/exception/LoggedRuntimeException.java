package at.ac.tuwien.sepm.assignment.individual.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

public abstract class LoggedRuntimeException extends RuntimeException{

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public LoggedRuntimeException() {
        super();
        LOGGER.error(this.getMessage(), this);
    }
    public LoggedRuntimeException(String message) {
        super(message);
        LOGGER.error(this.getMessage(), this);
    }
    public LoggedRuntimeException(Throwable cause) {
        super(cause);
        LOGGER.error(this.getMessage(), this);
    }
    public LoggedRuntimeException(String message, Throwable cause) {
        super(message, cause);
        LOGGER.error(this.getMessage(), this);
    }
}
