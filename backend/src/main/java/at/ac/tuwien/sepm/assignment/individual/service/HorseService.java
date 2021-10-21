package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseQueryParamsDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.exception.StateConflictException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;

import java.util.List;

/**
 * Service Object for Horses
 * Enables validation of incoming dtos and transferring the requests to the layers below.
 */
public interface HorseService {
    /**
     * Lists all horses stored in the system by search parameter qParams.
     *
     * @throws ValidationException if the argument is semantically incorrect
     * @throws StateConflictException if the argument conflicts with the state of the system
     * @throws ServiceException if an unexpected error in the layers below occurred
     *
     * @param qParams the query parameters of the search
     * @return list of all stored horses
     */
    List<Horse> allHorses(HorseQueryParamsDto qParams) throws ServiceException;

    /**
     * Creates a new horse in the system.
     *
     * @throws IllegalArgumentException it the argument is syntactically incorrect
     * @throws ValidationException if the argument is semantically incorrect
     * @throws StateConflictException if the argument conflicts with the state of the system
     * @throws ServiceException if an unexpected error in the layers below occurred
     *
     * @param dto the horse to create
     * @return the created horse
     */
    Horse createHorse(HorseDataDto dto) throws ServiceException;

    /**
     * Modifies an existing horse in the system.
     *
     * @throws NotFoundException if the horse with 'id' does not exist in the system
     * @throws IllegalArgumentException it the argument is syntactically incorrect
     * @throws ValidationException if the argument is semantically incorrect
     * @throws StateConflictException if the argument conflicts with the state of the system
     * @throws ServiceException if an unexpected error in the layers below occurred
     *
     * @param dto horse with the new data
     * @return the modified horse
     */
    Horse editHorse(long id, HorseDataDto dto) throws ServiceException;

    /**
     * Delete an existing horse in the system.
     *
     * @throws NotFoundException if the horse with 'id' does not exist in the system
     * @throws ValidationException if the argument is semantically incorrect
     * @throws ServiceException if an unexpected error in the layers below occurred
     *
     * @param id the horses id
     * @return the deleted horse
     */
    Horse deleteHorse(long id) throws ServiceException;

    /**
     * Retrieve a horse existing in the system by its id.
     *
     * @throws NotFoundException if the horse with 'id' does not exist in the system
     * @throws ValidationException if the argument is semantically incorrect
     * @throws ServiceException if an unexpected error in the layers below occurred
     *
     * @param id the horses id
     * @return the corresponding horse
     */
    Horse getHorse(long id) throws ServiceException;
}
