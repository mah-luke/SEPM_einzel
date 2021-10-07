package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import at.ac.tuwien.sepm.assignment.individual.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorseServiceImpl implements HorseService {
    private final HorseDao dao;
    private final Validator<HorseDto> validator;
    private final Validator<Long> idValidator;

    public HorseServiceImpl(HorseDao dao, Validator<HorseDto> validator, Validator<Long> idValidator) {
        this.dao = dao;
        this.validator = validator;
        this.idValidator = idValidator;
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
    public Horse createHorse(HorseDto dto) throws ServiceException {
        dto = validator.validate(dto);
        try {
            return dao.createHorse(dto);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Horse editHorse(HorseDto dto) throws ServiceException {
        dto = validator.validate(dto);
        idValidator.validate(dto.id());
        try {
            return dao.editHorse(dto);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Horse deleteHorse(Long id) throws ServiceException {
        id = idValidator.validate(id);
        try {
            return dao.deleteHorse(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
