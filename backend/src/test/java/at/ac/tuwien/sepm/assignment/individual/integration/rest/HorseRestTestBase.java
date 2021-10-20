package at.ac.tuwien.sepm.assignment.individual.integration.rest;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.base.TestSetup;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.IllegalArgumentException;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.RestException;
import at.ac.tuwien.sepm.assignment.individual.rest.HorseEndpoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class HorseRestTestBase {


    @Autowired
    private HorseEndpoint horseEndpoint;

    @Autowired
    private TestSetup testSetup;

    @Test
    @DisplayName("Creating of valid horse returns new dto with same values as passed")
    public void creating_of_valid_horse() throws RestException {
        HorseDataDto req = TestData.getValidHorseComplex();

        HorseDto res = horseEndpoint.createHorse(req);

        assert testSetup.dtoDerivedFromDataDto(res, req);
    }

    @Test
    @DisplayName("creating of horse with name=null throws IllegalArgumentException")
    public void creating_horse_with_name_null_throws_IllegalArgumentException() {
        HorseDataDto req = TestData.getNameNullHorseSimple();

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                horseEndpoint.createHorse(req)
        );
    }

    @Test
    @DisplayName("after deletion of only horse database empty -> no exception")
    public void db_empty_after_deletion_of_only_horse() throws RestException {
        Horse req = testSetup.addHorseToDatabase(TestData.getValidHorseComplex());

        horseEndpoint.deleteHorse(req.getId());

        assert testSetup.getHorsesFromDatabase().size() == 0;
    }

    @Test
    @DisplayName("Deletion of not existing horse throws NotFoundStatus")
    public void deletion_of_not_existing_throws_NotFoundStatus() {
        Assertions.assertThrows(NotFoundException.class, () -> horseEndpoint.deleteHorse(1));
    }


}
