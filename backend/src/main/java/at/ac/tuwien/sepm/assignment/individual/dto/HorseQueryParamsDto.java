package at.ac.tuwien.sepm.assignment.individual.dto;

import at.ac.tuwien.sepm.assignment.individual.enums.Sex;

import java.sql.Date;

public record HorseQueryParamsDto(String name, String description, Date dob, Sex sex, Long foodId) {
}
