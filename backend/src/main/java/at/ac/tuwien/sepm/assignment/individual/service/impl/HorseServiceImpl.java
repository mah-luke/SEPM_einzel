package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
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
    private final Validator<HorseDto> validator;

    public HorseServiceImpl(HorseDao dao, Validator<HorseDto> validator) {
        this.dao = dao;
        this.validator = validator;
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
            Long id = dao.createHorse(dto);
            return dao.getHorse(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Horse editHorse(HorseDto dto) throws ServiceException {
        dto = validator.validate(dto);
        try {
            Long id = dao.editHorse(dto);
            return dao.getHorse(id);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Horse deleteHorse(Long id) throws ServiceException {
        try {
            Horse horse = dao.getHorse(id);
            if (horse == null) throw new NotFoundException("ID of horse must be already contained in the database");
            dao.deleteHorse(id);
            return horse;
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
