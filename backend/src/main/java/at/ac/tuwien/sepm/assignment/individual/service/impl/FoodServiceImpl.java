package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.FoodDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.FoodQueryParamsDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Food;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.FoodDao;
import at.ac.tuwien.sepm.assignment.individual.service.FoodService;
import at.ac.tuwien.sepm.assignment.individual.service.ModelValidator;
import at.ac.tuwien.sepm.assignment.individual.service.Validator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    private final FoodDao dao;
    private final ModelValidator<FoodDataDto> validator;
    private final Validator<FoodQueryParamsDto> queryValidator;

    public FoodServiceImpl(FoodDao dao, ModelValidator<FoodDataDto> validator, Validator<FoodQueryParamsDto> queryValidator) {
        this.dao = dao;
        this.validator = validator;
        this.queryValidator = queryValidator;
    }

    @Override
    public List<Food> allFood(FoodQueryParamsDto qparams) throws ServiceException {
        qparams = queryValidator.validate(qparams);
        try {
            return dao.getAll(qparams);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Food getFood(long id) throws ServiceException {
        id = validator.validate(id);
        try {
            return dao.getFood(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Food createFood(FoodDataDto dto) throws ServiceException {
        dto = validator.validate(dto);
        try {
            return dao.createFood(dto);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
