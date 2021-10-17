package at.ac.tuwien.sepm.assignment.individual.dto;

import at.ac.tuwien.sepm.assignment.individual.enums.Sex;

import java.sql.Date;

/**
 * Class for Horse DTOs
 * Does not contain parents.
 */
public record ShallowHorseDto(long id, String name, String description, Date dob, Sex sex, FoodDto food) {

}
