package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseQueryParamsDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import at.ac.tuwien.sepm.assignment.individual.service.ModelValidator;
import at.ac.tuwien.sepm.assignment.individual.service.Validator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorseServiceImpl implements HorseService {
    private final HorseDao dao;
    private final ModelValidator<HorseDataDto> validator;
    private final Validator<HorseQueryParamsDto> queryParamsValidator;

    public HorseServiceImpl(HorseDao dao, ModelValidator<HorseDataDto> validator, Validator<HorseQueryParamsDto> queryParamsValidator) {
        this.dao = dao;
        this.validator = validator;
        this.queryParamsValidator = queryParamsValidator;
    }

    @Override
    public List<Horse> allHorses(HorseQueryParamsDto qParams)  {
        queryParamsValidator.validate(qParams);
        try {
            return dao.getAll(qParams);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Horse createHorse(HorseDataDto dto)  {
        validator.validate(dto);
        try {
            return dao.createHorse(dto);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Horse editHorse(long id, HorseDataDto dto)  {
        validator.validate(dto, id);
        try {
            return dao.editHorse(id, dto);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Horse deleteHorse(long id)  {
        validator.validate(id);
        try {
            return dao.deleteHorse(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Horse getHorse(long id)  {
        validator.validate(id);
        try {
            return dao.getHorse(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
