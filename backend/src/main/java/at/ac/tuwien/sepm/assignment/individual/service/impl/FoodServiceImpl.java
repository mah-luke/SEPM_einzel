package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.FoodDataDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Food;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.FoodDao;
import at.ac.tuwien.sepm.assignment.individual.service.FoodService;
import at.ac.tuwien.sepm.assignment.individual.service.Validator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    private final FoodDao dao;
    private final Validator<FoodDataDto> validator;

    public FoodServiceImpl(FoodDao dao, Validator<FoodDataDto> validator) {
        this.dao = dao;
        this.validator = validator;
        this.idValidator = idValidator;
    }

    @Override
    public List<Food> allFood() throws ServiceException {
        try {
            return dao.getAll();
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Food getFood(long id) throws ServiceException {
        id = validator.validateId(id);
        try {
            return dao.getFood(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Food createFood(FoodDataDto dto) throws ServiceException {
        dto = validator.validateObj(dto);
        try {
            return dao.createFood(dto);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
