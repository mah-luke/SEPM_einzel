package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.FoodQueryParamsDto;
import at.ac.tuwien.sepm.assignment.individual.exception.IllegalArgumentException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.service.Validator;
import org.springframework.stereotype.Component;

@Component
public class FoodQueryParamsValidator implements Validator<FoodQueryParamsDto> {

    static int MAX_LENGTH = 256;

    @Override
    public FoodQueryParamsDto validate(FoodQueryParamsDto qParams) throws ServiceException {

        // name
        if (qParams.name() != null) checkLength(qParams.name());

        // description
        if (qParams.description() != null) checkLength(qParams.description());

        // calories
        if (qParams.calories() != null && qParams.calories() < 0)
            throw new IllegalArgumentException("Given calories must not be below zero!");

        return qParams;
    }

    private String checkLength(String s){
        if (s != null && s.length() > MAX_LENGTH)
            throw new IllegalArgumentException("String " + s + "exceeds allowed limit of " + MAX_LENGTH + " chars!");
        return s;
    }
}
