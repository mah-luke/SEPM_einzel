package at.ac.tuwien.sepm.assignment.individual.dto;

import at.ac.tuwien.sepm.assignment.individual.enums.Sex;

import java.sql.Date;

/**
 * Class for Horse DTOs
 * Contains all common properties
 */
public record HorseDto(
        long id,
        String name,
        String description,
        Date dob,
        Sex sex,
        FoodDto food,
        ShallowHorseDto father,
        ShallowHorseDto mother
) {}
