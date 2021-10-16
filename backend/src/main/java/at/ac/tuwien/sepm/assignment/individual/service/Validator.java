package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;

import java.util.Map;

public interface Validator<Type> {

    /**
     * Validates a Type Object and returns its validated version
     *
     * @return the validated obj if valid
     */
    Type validate(Type obj) throws ServiceException;


}
