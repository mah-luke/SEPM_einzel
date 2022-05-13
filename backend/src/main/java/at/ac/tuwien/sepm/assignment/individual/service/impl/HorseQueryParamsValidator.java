package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.FoodDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseQueryParamsDto;
import at.ac.tuwien.sepm.assignment.individual.exception.*;
import at.ac.tuwien.sepm.assignment.individual.persistence.FoodDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.service.ModelValidator;
import at.ac.tuwien.sepm.assignment.individual.service.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class HorseQueryParamsValidator implements Validator<HorseQueryParamsDto> {

    private static final int MAX_LENGTH = 255;
    private final ModelValidator<FoodDataDto> foodValidator;
    private final ModelValidator<HorseDataDto> horseValidator;
    private final HorseDao horseDao;
    private final FoodDao foodDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    HorseQueryParamsValidator(ModelValidator<FoodDataDto> foodValidator, ModelValidator<HorseDataDto> horseValidator, HorseDao horseDao, FoodDao foodDao){
        this.foodValidator = foodValidator;
        this.horseValidator = horseValidator;
        this.horseDao = horseDao;
        this.foodDao = foodDao;
    }

    @Override
    public void validate(HorseQueryParamsDto qParams)  {
        LOGGER.debug("Validating dto: {}", qParams);

        // name
        checkLength(qParams.name());

        // description
        checkLength(qParams.description());

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

        // fatherId
        if (qParams.fatherId() != null) {
            horseValidator.validate(qParams.fatherId());
            try {
                horseDao.getHorse(qParams.fatherId());
            } catch (NotFoundException e) {
                throw new StateConflictException("Provided Father with id '" + qParams.fatherId() + "' does not exist!", e);
            } catch (PersistenceException e) {
                throw new ServiceException(e.getMessage(), e);
            }
        }

        // motherId
        if (qParams.motherId() != null) {
            horseValidator.validate(qParams.motherId());
            try {
                horseDao.getHorse(qParams.motherId());
            } catch (NotFoundException e) {
                throw new StateConflictException("Provided Mother with id '" + qParams.motherId() + "' does not exist!", e);
            } catch (PersistenceException e) {
                throw new ServiceException(e.getMessage(), e);
            }
        }

        // limit
        if (qParams.limit() != null && qParams.limit() < 1)
            throw new ValidationException("Limit of values must be positive");
    }

    private void checkLength(String s){
        if (s != null && s.length() > MAX_LENGTH)
            throw new ValidationException("String " + s + "exceeds allowed limit of " + MAX_LENGTH + " chars!");
    }
}
