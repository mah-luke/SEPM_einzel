package at.ac.tuwien.sepm.assignment.individual.dto;

import enums.Sex;

import java.sql.Date;

/**
 * Class for Horse DTOs
 * Contains all common properties except the id. Used for requests where horse data is needed (create, patch, etc.).
 */
public record HorseDataDto(String name, String description, Date dob, Sex sex, Long foodId) {

}
