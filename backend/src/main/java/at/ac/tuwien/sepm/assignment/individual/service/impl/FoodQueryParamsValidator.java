package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.FoodQueryParamsDto;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.service.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class FoodQueryParamsValidator implements Validator<FoodQueryParamsDto> {

    private static final int MAX_LENGTH = 255;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void validate(FoodQueryParamsDto qParams) {
        LOGGER.debug("Validating dto: {}", qParams);

        // name
        if (qParams.name() != null) checkLength(qParams.name());

        // description
        if (qParams.description() != null) checkLength(qParams.description());

        // calories
        if (qParams.calories() != null && qParams.calories() < 0)
            throw new ValidationException("Given calories must not be below zero!");

        // limit
        if (qParams.limit() != null && qParams.limit() < 1)
            throw new ValidationException("Limit of values must be positive");
    }

    private void checkLength(String s){
        if (s != null && s.length() > MAX_LENGTH)
            throw new ValidationException("String " + s + "exceeds allowed limit of " + MAX_LENGTH + " chars!");
    }
}
