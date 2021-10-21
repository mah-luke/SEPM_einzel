package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.dto.FoodDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.FoodQueryParamsDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Food;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;

import java.util.List;

/**
 * Data Access Object for Food.
 * Enables Access to the persistent data store for food.
 */
public interface FoodDao {
    /**
     * List food stored in the persistent data store by search parameters qParams
     *
     * @throws PersistenceException if an unexpected exception in the data store occured.
     *
     * @return a list of all stored food
     * @param qParams the valid query parameters of the search
     */
    List<Food> getAll(FoodQueryParamsDto qParams);

    /**
     * Get a specific food from the persistent data store by its id.
     *
     * @throws NotFoundException if the food with 'id' does not exist
     * @throws PersistenceException if an unexpected exception in the data store occured.
     *
     * @param id a valid id for a food.
     * @return the stored horse corresponding to the id
     */
    Food getFood(long id);

    /**
     * Create a new food and store it in the persistent data store.
     *
     * @throws PersistenceException if an unexpected exception in the data store occured.
     *
     * @param dto the valid food to create
     * @return the Food that was created
     */
    Food createFood(FoodDataDto dto);


}
