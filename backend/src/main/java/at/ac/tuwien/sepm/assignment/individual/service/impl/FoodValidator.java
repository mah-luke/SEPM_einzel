package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.FoodDataDto;
import at.ac.tuwien.sepm.assignment.individual.exception.IllegalArgumentException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.service.Validator;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FoodValidator  implements Validator<FoodDataDto> {

    static int MAX_LENGTH = 256;

    @Override
    public FoodDataDto validateObj(FoodDataDto dto) throws ValidationException {
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
    public long validateId(long id) {
        return id;
    }

    @Override
    public Map<String, String> validateQueryParams(Map<String, String> qParams) {
        final String MSG = "Query parameter ";

        for (String key : qParams.keySet()) {
            String val = qParams.get(key);

            if (val == null) throw new IllegalArgumentException(MSG + key + " was null!");
            else if (val.isBlank()) throw new IllegalArgumentException(MSG + key + " was empty!");
            checkLength(val);

            switch (key.toUpperCase()) {
                case "NAME", "DESCRIPTION" -> {}
                case "CALORIES" -> {
                    try {
                        Double.valueOf(val);
                    } catch(NumberFormatException e) {
                        throw new IllegalArgumentException(MSG + "calories could not be parsed to double!", e);
                    }
                }
                default -> throw new IllegalArgumentException("Got not supported query parameter: " + key);
            }
        }
        return qParams;
    }

    private String checkLength(String s){
        if (s != null && s.length() > MAX_LENGTH)
            throw new IllegalArgumentException("String " + s + "exceeds allowed limit of " + MAX_LENGTH + " chars!");
        return s;
    }
}
