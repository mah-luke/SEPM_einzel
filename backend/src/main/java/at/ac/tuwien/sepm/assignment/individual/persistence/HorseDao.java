package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseQueryParamsDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;

import java.util.List;

/**
 * Data Access Object for horses.
 * Enables access to the application's persistent data store for horses.
 */
public interface HorseDao {
    /**
     * List horses stored in the persistent data store by search parameter qParams.
     *
     * @throws PersistenceException if an unexpected exception in the data store occured.
     *
     * @return a list of all stored horses
     * @param qParams the valid query parameters
     */
    List<Horse> getAll(HorseQueryParamsDto qParams);

    /**
     * Get a specific horse in the persistent data store by its id.
     *
     * @throws PersistenceException if an unexpected exception in the data store occured.
     * @throws NotFoundException if the horse with 'id' does not exist.
     *
     * @param id a valid id of a stored horse
     * @return the stored horse corresponding to the id
     */
    Horse getHorse(long id);

    /**
     * Create a new horse and store it in the persistent data store.
     *
     * @throws PersistenceException if an unexpected exception in the data store occured.
     *
     * @param dto the valid horse to create
     * @return the id of the created horse
     */
    Horse createHorse(HorseDataDto dto);

    /**
     * Edit an already existing horse and store its modifications in the persistent data store.
     *
     * @throws PersistenceException if an unexpected exception in the data store occured.
     * @throws NotFoundException if the horse with 'id' does not exist.
     *
     * @param dto the horse with the new and valid data
     * @return the id of the edited horse
     */
    Horse editHorse(long id, HorseDataDto dto);

    /**
     * Delete an already existing horse from the persistent data store.
     *
     * @throws PersistenceException if an unexpected exception in the data store occured.
     * @throws NotFoundException if the horse to delete doesn't exist
     *
     * @param id the horses valid id
     */
    Horse deleteHorse(long id);
}
