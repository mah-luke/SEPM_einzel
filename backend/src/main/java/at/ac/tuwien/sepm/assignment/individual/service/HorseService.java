package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseQueryParamsDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * Service for working with horses.
 */
public interface HorseService {
    /**
     * Lists all horses stored in the system.
     *
     * @return list of all stored horses
     */
    List<Horse> allHorses(HorseQueryParamsDto qparams) throws ServiceException;

    /**
     * Creates a new horse in the system.
     *
     * @param dto the horse to create
     * @return the created horse
     */
    Horse createHorse(HorseDataDto dto) throws ServiceException;

    /**
     * Modifies an existing horse in the system.
     *
     * @param dto horse with the new data
     * @return the modified horse
     */
    Horse editHorse(long id, HorseDataDto dto) throws ServiceException;

    /**
     * Delete an existing horse in the system.
     *
     * @param id the horses id
     * @return the deleted horse
     */
    Horse deleteHorse(long id) throws ServiceException;

    /**
     * Retrieve an existing in the system.
     *
     * @param id the horses id
     * @return the corresponding horse
     */
    Horse getHorse(long id) throws ServiceException;
}
