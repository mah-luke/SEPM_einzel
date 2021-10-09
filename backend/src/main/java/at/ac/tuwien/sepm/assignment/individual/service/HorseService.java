package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;

import java.util.List;

/**
 * Service for working with horses.
 */
public interface HorseService {
    /**
     * Lists all horses stored in the system.
     *
     * @return list of all stored horses
     */
    List<Horse> allHorses() throws ServiceException;

    /**
     * Creates a new horse in the system.
     *
     * @param dto the horse to create
     * @return the created horse
     */
    Horse createHorse(HorseDto dto) throws ServiceException;

    /**
     * Modifies an existing horse in the system.
     *
     * @param dto horse with the new data
     * @return the modified horse
     */
    Horse editHorse(HorseDto dto) throws ServiceException;

    /**
     * Delete an existing horse in the system.
     *
     * @param id the horses id
     * @return the deleted horse
     */
    Horse deleteHorse(Long id) throws ServiceException;

    /**
     * Retrieve an existing in the system.
     *
     * @param id the horses id
     * @return the corresponding horse
     */
    Horse getHorse(long id) throws ServiceException;
}
