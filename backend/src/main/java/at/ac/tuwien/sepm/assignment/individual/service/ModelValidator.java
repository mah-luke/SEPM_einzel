package at.ac.tuwien.sepm.assignment.individual.service;


import at.ac.tuwien.sepm.assignment.individual.exception.*;
import at.ac.tuwien.sepm.assignment.individual.exception.IllegalArgumentException;

/**
 * Validates a Model (representing objects that get saved in the system).
 * e.g. a horse dto for creating and saving a horse entity.
 *
 * @param <Type>
 */
public interface ModelValidator<Type> extends Validator<Type> {
    /**
     * Validates an Object of <Type> that was already persisted in the system
     * Checks additionally whether the changes made for resource 'id' (already persisted in the system) that were passed
     * by 'obj' are allowed.
     *
     * @throws ServiceException if an unexpected exception is thrown by the layer below.
     * @throws IllegalArgumentException if the syntax of obj is not valid.
     * @throws ValidationException if the semantics of obj is not valid.
     * @throws StateConflictException if the state of the system conflicts with the requested changes of obj.
     *
     * @param obj the object to validate. References change to the object persisted with id 'id'.
     * @param id the id to validate.
     */
    void validate(Type obj, long id) throws ServiceException;

    /**
     * Validates the id for a Model of <Type>.
     *
     * @param id the id to validate.
     */
    void validate(long id);
}
