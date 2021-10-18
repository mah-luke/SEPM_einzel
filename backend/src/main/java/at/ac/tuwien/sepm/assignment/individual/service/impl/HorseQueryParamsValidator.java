package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.FoodDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseQueryParamsDto;
import at.ac.tuwien.sepm.assignment.individual.exception.*;
import at.ac.tuwien.sepm.assignment.individual.exception.IllegalArgumentException;
import at.ac.tuwien.sepm.assignment.individual.persistence.FoodDao;
import at.ac.tuwien.sepm.assignment.individual.service.ModelValidator;
import at.ac.tuwien.sepm.assignment.individual.service.Validator;
import org.springframework.stereotype.Component;

@Component
public class HorseQueryParamsValidator implements Validator<HorseQueryParamsDto> {

    static int MAX_LENGTH = 255;
    private final ModelValidator<FoodDataDto> foodValidator;
    private final FoodDao foodDao;

    HorseQueryParamsValidator(ModelValidator<FoodDataDto> foodValidator, FoodDao foodDao){
        this.foodValidator = foodValidator;
        this.foodDao = foodDao;
    }

    @Override
    public HorseQueryParamsDto validate(HorseQueryParamsDto qParams) throws ServiceException {

        // name
        if (qParams.name() != null) checkLength(qParams.name());

        // description
        if (qParams.description() != null) checkLength(qParams.description());

        // foodId
        if (qParams.foodId() != null) {
            foodValidator.validate(qParams.foodId());
            try {
                foodDao.getFood(qParams.foodId());
            } catch (NotFoundException e) {
                throw new StateConflictException("Provided Food with id '" + qParams.foodId() + "' does not exist!", e);
            } catch (PersistenceException e) {
                throw new ServiceException(e.getMessage(), e);
            }
        }

        // limit
        if (qParams.limit() != null && qParams.limit() < 1)
            throw new ValidationException("Limit of values must be positive");

        return qParams;
    }

    private String checkLength(String s){
        if (s != null && s.length() > MAX_LENGTH)
            throw new ValidationException("String " + s + "exceeds allowed limit of " + MAX_LENGTH + " chars!");
        return s;
    }
}
