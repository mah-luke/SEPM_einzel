package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.dto.FoodDataDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Food;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface FoodService {

    /**
     * Lists all food stored in the system.
     *
     * @return list of all stored food
     * @param qparams the query parameters of the search
     */
    List<Food> allFood(Map<String, String> qparams) throws ServiceException;

    /**
     * Retrieve an existing food in the system.
     *
     * @param id the food id
     * @return the corresponding food
     */
    Food getFood(long id) throws ServiceException;

    /**
     * Creates a new food in the system.
     *
     * @param dto the food to create
     * @return the created food
     */
    Food createFood(FoodDataDto dto) throws ServiceException;
}
