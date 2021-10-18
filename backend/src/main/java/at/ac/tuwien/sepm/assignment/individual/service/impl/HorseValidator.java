package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.FoodDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDataDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.enums.Sex;
import at.ac.tuwien.sepm.assignment.individual.exception.*;
import at.ac.tuwien.sepm.assignment.individual.exception.IllegalArgumentException;
import at.ac.tuwien.sepm.assignment.individual.persistence.FoodDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.service.ModelValidator;
import org.springframework.stereotype.Component;
import java.sql.Date;

@Component
public class HorseValidator implements ModelValidator<HorseDataDto> {

    private final FoodDao foodDao;
    private final HorseDao dao;
    private final ModelValidator<FoodDataDto> foodValidator;
    private final static int MAX_LENGTH = 256;

    HorseValidator(FoodDao foodDao, HorseDao dao, ModelValidator<FoodDataDto> foodValidator) {
        this.foodDao = foodDao;
        this.dao = dao;
        this.foodValidator = foodValidator;
    }

    // ASK: validator as service class valid?
    //      when IllegalArgument / Validation / StateConflict

    @Override
    public HorseDataDto validate(HorseDataDto dto) throws ServiceException {
        // name
        if (dto.name() == null) throw new IllegalArgumentException("Name for Horses must be set!");
        else if (dto.name().isBlank()) throw new ValidationException("Name must not be blank!");
        checkLength(dto.name());

        // description
        checkLength(dto.description());

        // dob
        if (dto.dob() == null) throw new IllegalArgumentException("Dob for Horses must be set!");
        else if (dto.dob().after(new Date(System.currentTimeMillis())))
            throw new StateConflictException("Dob must not be in the future!");

        // sex
        if (dto.sex() == null) throw new IllegalArgumentException("Sex for Horses must be set!");

        // foodId
        if (dto.foodId() != null) {
            foodValidator.validate(dto.foodId());
            try {
                foodDao.getFood(dto.foodId());
            } catch (NotFoundException e) {
                throw new StateConflictException("Provided Food with id '" + dto.foodId() + "' does not exist!", e); // ASK: check if handling is valid
            } catch (PersistenceException e) {
                throw new ServiceException(e.getMessage(), e);
            }
        }

        // fatherId
        if (dto.fatherId() != null){
            validate(dto.fatherId());
            try {
                Horse father = dao.getHorse(dto.fatherId());
                if (father.getSex() != Sex.Male) throw new ValidationException("Provided Father must be Male!");
                if (father.getDob().after(dto.dob())) throw new StateConflictException("Provided Father must be older than horse!");
            } catch (NotFoundException e) {
                throw new StateConflictException("Provided Father with id '" + dto.fatherId() + "' does not exist!", e);
            } catch (PersistenceException e) {
                throw new ServiceException(e.getMessage(), e);
            }
        }

        // motherId
        if (dto.motherId() != null){
            validate(dto.motherId());
            try {
                Horse mother = dao.getHorse(dto.motherId());
                if (mother.getSex() != Sex.Female) throw new ValidationException("Provided Mother must be Female!");
                if (mother.getDob().after(dto.dob())) throw new StateConflictException("Provided Mother must be older than horse!");
            } catch (NotFoundException e) {
                throw new StateConflictException("Provided Mother with id '" + dto.motherId() + "' does not exist!", e);
            } catch (PersistenceException e) {
                throw new ServiceException(e.getMessage(), e);
            }
        }
        return dto;
    }

    @Override
    public long validate(long id) {
        if (id < 1) throw new ValidationException("Id not positive: " + id);
        return id;
    }

    private String checkLength(String s){
        if (s != null && s.length() > MAX_LENGTH)
            throw new ValidationException("String starting with '" + s.substring(0,20) + "' exceeds allowed limit of " + MAX_LENGTH + " chars!");
        return s;
    }
}
