package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.dto.FoodDataDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Food;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;

import java.util.List;

public interface FoodDao {
    /**
     * Get all foods stored in the persistent data store.
     *
     * @return a list of all stored Food
     */
    List<Food> getAll() throws PersistenceException;

    /**
     * Get a specific food in the persistent data store by its id.
     *
     * @param id a valid id of a stored horse
     * @return the stored horse corresponding to the id
     */
    Food getFood(Long id) throws PersistenceException;

    /**
     * Create a new food and store it in the persistent data store.
     *
     * @param dto the Food to create
     * @return the id of the created Food
     */
    Food createFood(FoodDataDto dto) throws PersistenceException;


}
