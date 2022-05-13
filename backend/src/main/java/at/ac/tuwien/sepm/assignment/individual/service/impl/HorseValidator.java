package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.FoodDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseQueryParamsDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.enums.Sex;
import at.ac.tuwien.sepm.assignment.individual.exception.*;
import at.ac.tuwien.sepm.assignment.individual.exception.IllegalArgumentException;
import at.ac.tuwien.sepm.assignment.individual.persistence.FoodDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.service.ModelValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.List;

@Component
public class HorseValidator implements ModelValidator<HorseDataDto> {

    private final FoodDao foodDao;
    private final HorseDao dao;
    private final ModelValidator<FoodDataDto> foodValidator;
    private final static int MAX_LENGTH = 255;
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    HorseValidator(FoodDao foodDao, HorseDao dao, ModelValidator<FoodDataDto> foodValidator) {
        this.foodDao = foodDao;
        this.dao = dao;
        this.foodValidator = foodValidator;
    }

    @Override
    public void validate(HorseDataDto dto, long id)  {
        LOGGER.debug("Validating dto: {}, id: {}", dto, id);

        validate(id);

        validate(dto);

        if (dto.motherId() != null && id == dto.motherId() ||
                dto.fatherId() != null && id == dto.fatherId())
            throw new StateConflictException("A Horse must never have itself as parent!");

        try {
            // get horse
            Horse old = dao.getHorse(id);

            // children
            List<Horse> children = dao.getAll(
                    new HorseQueryParamsDto(
                            null,
                            null,
                            null,
                            null,
                            null,
                            old.getSex() == Sex.Male ? id : null,
                            old.getSex() == Sex.Female ? id : null,
                            null)
            );

            if (!children.isEmpty()) {
                if (old.getSex() != dto.sex())
                    throw new StateConflictException("Horse must not change its sex when it has children!");

                Horse oldest = children.get(0);
                for (Horse child : children) {
                    if (child.getDob().isBefore(oldest.getDob())) oldest = child;
                }
                if (!oldest.getDob().isAfter(dto.dob())) throw new StateConflictException(
                        "Children must always be younger than their parents!\n" +
                                " This horse must have an Date of Birth before " + oldest.getDob().toString() +
                                " (Date of Birth of oldest child)!"
                );
            }
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }

    }

    @Override
    public void validate(long id) {
        LOGGER.debug("Validating id: {}", id);

        if (id == 0) throw new ValidationException("Id must not be 0!");
    }

    private void checkLength(String s){
        if (s != null && s.length() > MAX_LENGTH)
            throw new ValidationException("String starting with '" + s.substring(0,20) + "' exceeds allowed limit of " + MAX_LENGTH + " chars!");
    }

    @Override
    public void validate(HorseDataDto dto)  {
        LOGGER.debug("Validating dto: {}", dto);
        // name
        if (dto.name() == null) throw new IllegalArgumentException("Name for Horses must be set!");
        else if (dto.name().isBlank()) throw new ValidationException("Name must not be blank!");
        checkLength(dto.name());

        // description
        checkLength(dto.description());

        // dob
        if (dto.dob() == null) throw new IllegalArgumentException("Dob for Horses must be set!");
        else if (dto.dob().isAfter(LocalDate.now()))
            throw new ValidationException("Dob must not be in the future!");

        // sex
        if (dto.sex() == null) throw new IllegalArgumentException("Sex for Horses must be set!");

        // foodId
        if (dto.foodId() != null) {
            foodValidator.validate(dto.foodId());
            try {
                foodDao.getFood(dto.foodId());
            } catch (NotFoundException e) {
                throw new StateConflictException("Provided Food with id '" + dto.foodId() + "' does not exist!", e);
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
                if (!father.getDob().isBefore(dto.dob())) throw new StateConflictException("Provided Father must be older than horse!");
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
                if (!mother.getDob().isBefore(dto.dob())) throw new StateConflictException("Provided Mother must be older than horse!");
            } catch (NotFoundException e) {
                throw new StateConflictException("Provided Mother with id '" + dto.motherId() + "' does not exist!", e);
            } catch (PersistenceException e) {
                throw new ServiceException(e.getMessage(), e);
            }
        }
    }
}
