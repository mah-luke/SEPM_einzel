package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.exception.IllegalArgumentException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.exception.StateConflictException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;

/**
 * A Validator that validates dtos of <Type> that may or may not be saved in the system.
 *
 * @param <Type> the type of the object to validate.
 */
public interface Validator<Type> {

    /**
     * Validates an dto of <Type> that has not been persisted in the system.
     *
     * @throws ServiceException if an unexpected exception is thrown by the layer below.
     * @throws IllegalArgumentException if the syntax of obj is not valid.
     * @throws ValidationException if the semantics of obj is not valid.
     * @throws StateConflictException if the state of the system conflicts with obj.
     *
     * @param obj the object to validate.
     */
    void validate(Type obj);


}
