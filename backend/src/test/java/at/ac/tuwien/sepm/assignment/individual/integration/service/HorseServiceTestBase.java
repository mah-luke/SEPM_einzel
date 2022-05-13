package at.ac.tuwien.sepm.assignment.individual.integration.service;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.base.TestSetup;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.*;
import at.ac.tuwien.sepm.assignment.individual.exception.IllegalArgumentException;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class HorseServiceTestBase {

    @Autowired
    HorseService horseService;

    @Autowired
    TestSetup testSetup;

    @Test
    @DisplayName("Creating of Horse with name=null throws IllegalArgumentException")
    public void creating_of_NameNull_throws_IllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                horseService.createHorse(TestData.getNameNullHorseSimple()));
    }

    @Test
    @DisplayName("Getting of not existing horse throws a NotFoundException")
    public void getting_of_not_existing_throws_NotFoundException() {
        Assertions.assertThrows(NotFoundException.class, () ->
                horseService.getHorse(1));
    }

    @Test
    @DisplayName("Getting of existing horse -> no exception")
    public void getting_of_existing_horse() throws ServiceException {
        Horse addedHorse = testSetup.addHorseToDatabase(TestData.getValidHorseComplex());

        Horse get = horseService.getHorse(addedHorse.getId());

        assert get.equals(addedHorse);
    }

    @Test
    @DisplayName("Creating of Horse with dob in future throws ValidationException")
    public void creating_of_dob_in_future_throws_ValidationException() {
        Assertions.assertThrows(ValidationException.class, () ->
                horseService.createHorse(TestData.getDobFutureHorseSimple()));
    }
}
