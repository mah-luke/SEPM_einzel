package at.ac.tuwien.sepm.assignment.individual.dto;

import at.ac.tuwien.sepm.assignment.individual.enums.Sex;

import java.sql.Date;

public record FoodQueryParamsDto(String name, String description, Double calories) {
}
