package at.ac.tuwien.sepm.assignment.individual.mapper;

import at.ac.tuwien.sepm.assignment.individual.dto.FoodDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Food;
import org.springframework.stereotype.Component;

@Component
public class FoodMapper {

    public FoodDto entityToDto(Food food) {
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
