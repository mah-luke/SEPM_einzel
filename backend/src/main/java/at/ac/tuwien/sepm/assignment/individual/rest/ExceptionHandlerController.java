package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.exception.*;
import at.ac.tuwien.sepm.assignment.individual.exception.IllegalArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.lang.invoke.MethodHandles;

@ControllerAdvice
public class ExceptionHandlerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @ExceptionHandler(value = ValidationException.class)
    public void handleValidationException(HttpServletResponse res, Exception e) throws Exception {
        LOGGER.trace("handleException({})", e.getClass().getName());
        LOGGER.error(e.getMessage(), e); // log all exceptions
        res.sendError(422, e.getMessage());
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public void handleIllegalArgumentException(HttpServletResponse res, Exception e) throws Exception {
        LOGGER.trace("handleException({})", e.getClass().getName());
        LOGGER.error(e.getMessage(), e); // log all exceptions
        res.sendError(400, e.getMessage());
    }

    @ExceptionHandler(value = StateConflictException.class)
    public void handleStateConflictException(HttpServletResponse res, Exception e) throws Exception {
        LOGGER.trace("handleException({})", e.getClass().getName());
        LOGGER.error(e.getMessage(), e); // log all exceptions
        res.sendError(409, e.getMessage());
    }

    @ExceptionHandler(value = NotFoundException.class)
    public void handleNotFoundException(HttpServletResponse res, Exception e) throws Exception {
        LOGGER.trace("handleException({})", e.getClass().getName());
        LOGGER.error(e.getMessage(), e); // log all exceptions
        res.sendError(404, e.getMessage());
    }

}
