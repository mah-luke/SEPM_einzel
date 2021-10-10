package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDataDto;
import at.ac.tuwien.sepm.assignment.individual.exception.*;
import at.ac.tuwien.sepm.assignment.individual.exception.IllegalArgumentException;
import at.ac.tuwien.sepm.assignment.individual.persistence.FoodDao;
import at.ac.tuwien.sepm.assignment.individual.service.Validator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Date;

@Service
public class HorseValidator implements Validator<HorseDataDto> {

    private final Validator<Long> idValidator;
    private final FoodDao foodDao;
    private final static int MAX_LENGTH = 256;

    HorseValidator(Validator<Long> idValidator, FoodDao foodDao) {
        this.idValidator = idValidator;
        this.foodDao = foodDao;
    }

    // ASK: validator as service class valid?
    //      when IllegalArgument / Validation / StateConflict

    @Override
    public HorseDataDto validate(HorseDataDto dto) throws ServiceException {
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

        // foodId
        idValidator.validate(dto.foodId());
        try {
            foodDao.getFood(dto.foodId());
        } catch (NotFoundException e) {
            throw new ValidationException("Provided Food with id '" + dto.foodId() + "' does not exist!", e); // ASK: check if handling is valid
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }


        return dto;
    }

    private String checkLength(String s){
        if (s != null && s.length() > MAX_LENGTH)
            throw new IllegalArgumentException("String '" + s + "' exceeds allowed limit of " + MAX_LENGTH + " chars!");
        return s;
    }
}
