package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;

public interface Validator<Type> {

    /**
     * Validates an object and returns it
     *
     * @return if valid: validated Object,
     *             else: null
     */
    Type validate(Type obj) throws ValidationException, ServiceException;
}
