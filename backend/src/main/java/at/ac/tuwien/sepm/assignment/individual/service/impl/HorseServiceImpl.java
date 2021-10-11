package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.FoodDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDataDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import at.ac.tuwien.sepm.assignment.individual.service.Validator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorseServiceImpl implements HorseService {
    private final HorseDao dao;
    private final Validator<HorseDataDto> validator;
    private final Validator<FoodDataDto> foodValidator;

    public HorseServiceImpl(HorseDao dao, Validator<HorseDataDto> validator, Validator<FoodDataDto> foodValidator) {
        this.dao = dao;
        this.validator = validator;
        this.foodValidator = foodValidator;
    }

    @Override
    public List<Horse> allHorses() throws ServiceException {
        try {
            return dao.getAll();
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Horse createHorse(HorseDataDto dto) throws ServiceException {
        dto = validator.validateObj(dto);
        try {
            return dao.createHorse(dto);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Horse editHorse(long id, HorseDataDto dto) throws ServiceException {
        foodValidator.validateId(id);
        dto = validator.validateObj(dto);
        try {
            return dao.editHorse(id, dto);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Horse deleteHorse(long id) throws ServiceException {
        id = validator.validateId(id);
        try {
            return dao.deleteHorse(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Horse getHorse(long id) throws ServiceException {
        id = validator.validateId(id);
        try {
            return dao.getHorse(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
