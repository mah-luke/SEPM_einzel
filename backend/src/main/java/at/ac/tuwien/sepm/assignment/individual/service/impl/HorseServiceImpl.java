package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
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
    public List<Horse> allHorses() {
        return dao.getAll();
    }

    @Override
    public Horse createHorse(HorseDto dto) {
        dto = validator.validate(dto);
        Long id = dao.createHorse(dto);
        return dao.getHorse(id);
    }

    @Override
    public Horse editHorse(HorseDto dto) {
        dto = validator.validate(dto);
        Long id = dao.editHorse(dto);
        return dao.getHorse(id);
    }
}
