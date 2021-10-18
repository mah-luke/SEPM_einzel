package at.ac.tuwien.sepm.assignment.individual.dto;

import at.ac.tuwien.sepm.assignment.individual.enums.Sex;

import java.time.LocalDate;

/**
 * Class for Horse DTOs
 * Contains all common properties except the id. Used for requests where horse data is needed (create, patch, etc.).
 */
public record HorseDataDto(String name, String description, LocalDate dob, Sex sex, Long foodId, Long fatherId, Long motherId) {

}
