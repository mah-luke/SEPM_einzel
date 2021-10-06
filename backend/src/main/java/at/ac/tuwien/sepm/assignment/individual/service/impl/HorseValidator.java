package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.service.Validator;
import org.springframework.stereotype.Component;

@Component
public class HorseValidator implements Validator<HorseDto> {

    @Override
    public HorseDto validate(HorseDto dto) throws ValidationException {
//        TODO: implement validation
        return dto;
    }
}
