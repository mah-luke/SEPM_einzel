package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.FoodDataDto;
import at.ac.tuwien.sepm.assignment.individual.exception.IllegalArgumentException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.service.ModelValidator;
import org.springframework.stereotype.Component;

@Component
public class FoodValidator  implements ModelValidator<FoodDataDto> {

    static int MAX_LENGTH = 256;

    @Override
    public FoodDataDto validate(FoodDataDto dto) throws ValidationException {
        // name
        if (dto.name() == null) throw new IllegalArgumentException("Name for Food must be set!");
        else if (dto.name().isBlank()) throw new IllegalArgumentException("Name must not be blank!");
        checkLength(dto.name());

        // description
        checkLength(dto.description());

        // calories

        return dto;
    }

    @Override
    public long validate(long id) {
        return id;
    }



    private String checkLength(String s){
        if (s != null && s.length() > MAX_LENGTH)
            throw new IllegalArgumentException("String " + s + "exceeds allowed limit of " + MAX_LENGTH + " chars!");
        return s;
    }
}
