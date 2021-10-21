package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.dto.FoodDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.FoodQueryParamsDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Food;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;

import java.util.List;

/**
 * Service Object for Food
 * Enables validation of incoming dtos and transferring the requests to the layers below.
 */
public interface FoodService {

    /**
     * Lists food stored to the system by search parameter qParams.
     *
     * @throws IllegalArgumentException it the argument is syntactically incorrect
     * @throws ValidationException if the argument is semantically incorrect
     * @throws ServiceException if an unexpected error in the layers below occurred
     *
     * @return list of all stored food
     * @param qParams the query parameters of the search
     */
    List<Food> allFood(FoodQueryParamsDto qParams) throws ServiceException;

    /**
     * Retrieve an existing food in the system by its id.
     *
     * @throws IllegalArgumentException it the argument is syntactically incorrect
     * @throws ValidationException if the argument is semantically incorrect
     * @throws NotFoundException if the requested food for 'id' does not exist
     * @throws ServiceException if an unexpected error in the layers below occurred
     *
     * @param id an id of a food
     * @return the corresponding food
     */
    Food getFood(long id) throws ServiceException;

    /**
     * Creates a new food in the system.
     *
     * @throws IllegalArgumentException it the argument is syntactically incorrect
     * @throws ValidationException if the argument is semantically incorrect
     * @throws ServiceException if an unexpected error in the layers below occurred
     *
     * @param dto the food to create
     * @return the created food
     */
    Food createFood(FoodDataDto dto) throws ServiceException;
}
