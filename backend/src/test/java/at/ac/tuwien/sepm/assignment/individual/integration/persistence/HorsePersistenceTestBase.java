package at.ac.tuwien.sepm.assignment.individual.integration.persistence;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.base.TestSetup;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDataDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class HorsePersistenceTestBase {

    @Autowired
    HorseDao horseDao;

    @Autowired
    TestSetup testSetup;

    @Test
    @DisplayName("Getting of not existing horse id throws NotFoundException")
    public void getting_of_notExisting_throws_NotFoundException() {
        Assertions.assertThrows(NotFoundException.class, () ->
            horseDao.getHorse(1)
        );
    }

    @Test
    @DisplayName("creation of a valid horse -> no exception")
    public void creation_of_valid_horse() {
        HorseDataDto validDto = TestData.getValidHorseSimple();
        Horse retHorse = horseDao.createHorse(validDto);
        Horse getHorse = testSetup.getHorsesFromDatabase().get(0);

        assert retHorse.equals(getHorse);
        assert testSetup.entityDerivedFromDto(getHorse, validDto);
    }

    @Test
    @DisplayName("editing of an existing horse -> no exception")
    public void editing_of_existing_horse_with_valid_horse() {
        Horse startHorse = testSetup.addHorseToDatabase(TestData.getValidHorseSimple());
        HorseDataDto submittedHorse = TestData.getValidHorseComplex();
        Horse returnedHorse = horseDao.editHorse(startHorse.getId(), submittedHorse);

        assert testSetup.entityDerivedFromDto(returnedHorse, submittedHorse);
    }

    @Test
    @DisplayName("getting of getAll and get returns equal object -> no exception")
    public void getting_by_id_same_as_all() {
        Horse addedHorse = testSetup.addHorseToDatabase(TestData.getValidHorseComplex());

        Horse horseByAll = horseDao.getAll(TestData.getEmptyQuery()).get(0);
        Horse horseById = horseDao.getHorse(addedHorse.getId());

        assert horseByAll.equals(horseById);
    }
}
