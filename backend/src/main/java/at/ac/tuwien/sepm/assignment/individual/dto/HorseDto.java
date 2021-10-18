package at.ac.tuwien.sepm.assignment.individual.dto;

import at.ac.tuwien.sepm.assignment.individual.enums.Sex;

import java.time.LocalDate;

/**
 * Class for Horse DTOs
 * Contains all common properties
 */
public record HorseDto(
        long id,
        String name,
        String description,
        LocalDate dob,
        Sex sex,
        FoodDto food,
        ShallowHorseDto father,
        ShallowHorseDto mother
) {}
