package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDataDto;
import at.ac.tuwien.sepm.assignment.individual.exception.IllegalArgumentException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.service.Validator;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class HorseValidator implements Validator<HorseDataDto> {

    private final static int MAX_LENGTH = 256;

    @Override
    public HorseDataDto validate(HorseDataDto dto) {
        // name
        if (dto.name() == null) throw new IllegalArgumentException("Name for Horses must be set!");
        else if (dto.name().isBlank()) throw new IllegalArgumentException("Name must not be blank!");
        checkLength(dto.name());

        // description
        checkLength(dto.description());

        // dob
        if (dto.dob() == null) throw new IllegalArgumentException("Dob for Horses must be set!");
        else if (dto.dob().after(new Date(System.currentTimeMillis())))
            throw new ValidationException("Dob must not be in the future!");

        // sex
        if (dto.sex() == null) throw new IllegalArgumentException("Sex for Horses must be set!");

        return dto;
    }

    private String checkLength(String s){
        if (s != null && s.length() > MAX_LENGTH)
            throw new IllegalArgumentException("String " + s + "exceeds allowed limit of " + MAX_LENGTH + " chars!");
        return s;
    }
}
