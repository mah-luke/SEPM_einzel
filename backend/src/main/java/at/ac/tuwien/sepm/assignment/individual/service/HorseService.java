package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import java.util.List;

/**
 * Service for working with horses.
 */
public interface HorseService {
    /**
     * Lists all horses stored in the system.
     *
     * @return list of all stored horses
     */
    List<Horse> allHorses();

    /**
     * Creates a new horse in the system.
     *
     * @param dto the horse to create
     * @return the created horse
     */
    Horse createHorse(HorseDto dto);

    /**
     * Modifies an existing horse in the system.
     *
     * @param dto horse with the new data
     * @return the modified horse
     */
    Horse editHorse(HorseDto dto);
}
