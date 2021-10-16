package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseQueryParamsDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;

import java.util.List;

/**
 * Data Access Object for horses.
 * Implements access functionality to the application's persistent data store regarding horses.
 */
public interface HorseDao {
    /**
     * Get all horses stored in the persistent data store.
     *
     * @return a list of all stored horses
     * @param qParams the query parameters
     */
    List<Horse> getAll(HorseQueryParamsDto qParams);

    /**
     * Get a specific horse in the persistent data store by its id.
     *
     * @param id a valid id of a stored horse
     * @return the stored horse corresponding to the id
     */
    Horse getHorse(long id);

    /**
     * Create a new horse and store it in the persistent data store.
     *
     * @param dto the horse to create
     * @return the id of the created horse
     */
    Horse createHorse(HorseDataDto dto);

    /**
     * Edit an already existing horse and store the modifications in the persistent data store.
     *
     * @param dto the horse with the new data
     * @return the id of the edited horse
     */
    Horse editHorse(long id, HorseDataDto dto);

    /**
     * Delete an already existing horse from the persistent data store.
     *
     * @param id the horses id
     */
    Horse deleteHorse(long id);
}
