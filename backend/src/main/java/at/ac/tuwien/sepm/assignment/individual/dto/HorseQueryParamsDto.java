package at.ac.tuwien.sepm.assignment.individual.dto;

import at.ac.tuwien.sepm.assignment.individual.enums.Sex;

import java.time.LocalDate;

public record HorseQueryParamsDto(String name, String description, LocalDate dob, Sex sex, Long foodId, Long limit) {
}
