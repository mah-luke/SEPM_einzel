package at.ac.tuwien.sepm.assignment.individual.mapper;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.ShallowHorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.service.FoodService;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class HorseMapper {

    private final FoodMapper foodMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final HorseService service;
    private final FoodService foodService;

    public HorseMapper(FoodMapper foodMapper, HorseService service, FoodService foodService) {
        this.foodMapper = foodMapper;
        this.service = service;
        this.foodService = foodService;
    }

    public HorseDto entityToDto(Horse horse) throws ServiceException {
        LOGGER.debug("Mapping {} to HorseDto", horse);

        if (horse == null) return null;

        return new HorseDto(
                horse.getId(),
                horse.getName(),
                horse.getDescription(),
                horse.getDob(),
                horse.getSex(),
                foodMapper.entityToDto(horse.getFoodId() != null? foodService.getFood(horse.getFoodId()) : null),
                entityToShallowDto(horse.getFatherId() != null ? service.getHorse(horse.getFatherId()) : null),
                entityToShallowDto(horse.getMotherId() != null ? service.getHorse(horse.getMotherId()) : null)
        );
    }

    public ShallowHorseDto entityToShallowDto(Horse horse) {
        LOGGER.debug("Mapping {} to ShallowHorseDto", horse);

        if (horse == null) return null;

        return new ShallowHorseDto(
                horse.getId(),
                horse.getName(),
                horse.getDescription(),
                horse.getDob(),
                horse.getSex()
        );
    }
}
