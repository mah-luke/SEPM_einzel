package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;

import java.util.Map;

public interface Validator<Type> {

    /**
     * Validates a Type Object and returns it
     *
     * @return the validated obj if valid
     */
    Type validateObj(Type obj) throws ServiceException;

    /**
     * Validates an id corresponding to a Type Object.
     *
     * @param id the id to validate.
     * @return the validated id if valid
     */
    long validateId(long id);

    /**
     * Validates Query Parameters corresponding to a Type Object.
     *
     * @param queryParams the params to validate
     * @return the validated query if valid
     */
    Map<String, String> validateQueryParams(Map<String, String> queryParams);
}
