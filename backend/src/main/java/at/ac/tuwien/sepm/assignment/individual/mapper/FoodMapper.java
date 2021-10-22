package at.ac.tuwien.sepm.assignment.individual.mapper;

import at.ac.tuwien.sepm.assignment.individual.dto.FoodDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Food;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class FoodMapper {

    private final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public FoodDto entityToDto(Food food) {
        LOGGER.debug("Mapping {} to FoodDto", food);

        if (food == null) return null;

        return new FoodDto(
                food.getId(),
                food.getName(),
                food.getDescription(),
                food.getCalories()
        );
    }

    public Food dtoToEntity(FoodDto foodDto) {
        if (foodDto == null) return null;

        Food food = new Food();
        food.setId(foodDto.id());
        food.setName(foodDto.name());
        food.setDescription(foodDto.description());
        food.setCalories(foodDto.calories());

        return food;
    }
}
