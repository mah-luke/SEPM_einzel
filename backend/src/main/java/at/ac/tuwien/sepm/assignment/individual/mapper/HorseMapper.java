package at.ac.tuwien.sepm.assignment.individual.mapper;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.ShallowHorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class HorseMapper {

    private final FoodMapper foodMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public HorseMapper(FoodMapper foodMapper) {
        this.foodMapper = foodMapper;
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
                foodMapper.entityToDto(horse.getFood()),
                entityToShallowDto(horse.getFather()),
                entityToShallowDto(horse.getMother())
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
