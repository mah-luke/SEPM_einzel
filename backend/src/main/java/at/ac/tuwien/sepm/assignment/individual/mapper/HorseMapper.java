package at.ac.tuwien.sepm.assignment.individual.mapper;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.ShallowHorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.ShallowHorse;
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
                entityToDto(horse.getFather()),
                entityToDto(horse.getMother())
        );
    }

    public ShallowHorseDto entityToDto(ShallowHorse horse) {
        if (horse == null) return null;

        return new ShallowHorseDto(
                horse.getId(),
                horse.getName(),
                horse.getDescription(),
                horse.getDob(),
                horse.getSex(),
                foodMapper.entityToDto(horse.getFood())
        );
    }

    public ShallowHorse entityToShallowEntity(Horse horse) {
        if (horse == null) return null;

        return new ShallowHorse(
          horse.getId(),
          horse.getName(),
          horse.getDescription(),
          horse.getDob(),
          horse.getSex(),
          horse.getFood()
        );
    }

    public Horse dtoToEntity(HorseDto horseDto) {
        if (horseDto == null) return null;

        Horse horse = new Horse();
        horse.setId(horseDto.id());
        horse.setName(horseDto.name());
        horse.setDescription(horseDto.description());
        horse.setDob(horseDto.dob());
        horse.setSex(horseDto.sex());
        horse.setFood(foodMapper.dtoToEntity(horseDto.food()));

        return horse;
    }
}
