package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.FoodDataDto;
import at.ac.tuwien.sepm.assignment.individual.exception.IllegalArgumentException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.service.ModelValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class FoodValidator  implements ModelValidator<FoodDataDto> {

    private static final int MAX_LENGTH = 255;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void validate(FoodDataDto dto) {
        LOGGER.debug("Validating dto: {}", dto);
        // name
        if (dto.name() == null) throw new IllegalArgumentException("Name for Food must be set!");
        else if (dto.name().isBlank()) throw new ValidationException("Name must not be blank!");
        checkLength(dto.name());

        // description
        checkLength(dto.description());

        // calories
        if (dto.calories() != null && dto.calories() < 0)
            throw new ValidationException("Given calories must not be below zero!");
    }

    @Override
    public void validate(FoodDataDto dto, long id) {
        LOGGER.debug("Validating dto: {}, id: {}", dto, id);
        validate(id);
        validate(dto);
    }

    @Override
    public void validate(long id) {
        LOGGER.debug("Validating, id: {}", id);
        if (id == 0) throw new ValidationException("Id must never be 0!");
    }

    private String checkLength(String s){
        if (s != null && s.length() > MAX_LENGTH)
            throw new ValidationException("String '" + s.substring(0,20) + "' exceeds allowed limit of " + MAX_LENGTH + " chars!");
        return s;
    }
}
