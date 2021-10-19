package at.ac.tuwien.sepm.assignment.individual.dto;

import at.ac.tuwien.sepm.assignment.individual.enums.Sex;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

public record HorseQueryParamsDto(String name, String description, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob, Sex sex, Long foodId, Long limit) {
}
