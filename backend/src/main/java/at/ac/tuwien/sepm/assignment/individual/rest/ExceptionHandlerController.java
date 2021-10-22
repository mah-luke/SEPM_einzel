package at.ac.tuwien.sepm.assignment.individual.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.lang.invoke.MethodHandles;

@ControllerAdvice
public class ExceptionHandlerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    // ASK: rethrowing like this ok???

    @ExceptionHandler(value = Exception.class)
    public void handleException(Exception e) throws Exception {
        LOGGER.trace("handleException({})", e.getClass().getName());
        LOGGER.error(e.getMessage(), e); // log all exceptions
        throw e; // let Spring handle the response to the client in the default manner
    }

//    @ExceptionHandler(value = ValidationException.class)
//    public void handleValidationException(HttpServletResponse res, Exception e) throws Exception {
//        LOGGER.error(e.getMessage(), e); // log all exceptions
//        res.sendError(422, e.getMessage());
//    }

}
