package at.ac.tuwien.sepm.assignment.individual.mapper;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.ShallowHorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import org.springframework.stereotype.Component;

@Component
public class HorseMapper {

    private final FoodMapper foodMapper;

    public HorseMapper(FoodMapper foodMapper) {
        this.foodMapper = foodMapper;
    }

    public HorseDto entityToDto(Horse horse) {
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
