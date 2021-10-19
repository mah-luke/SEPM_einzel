package at.ac.tuwien.sepm.assignment.individual.base;

import at.ac.tuwien.sepm.assignment.individual.dto.FoodDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseQueryParamsDto;
import at.ac.tuwien.sepm.assignment.individual.enums.Sex;

import java.time.LocalDate;

public class TestData {

    /**
     * URI Data
     */

    public static final String BASE_URL = "http://localhost:";
    public static final String HORSE_URL = "/horses";
    public static final String FOOD_URL = "/food";

    /**
     * Date Data
     */

    public static final LocalDate TODAY = LocalDate.now();
    public static final LocalDate LASTYEAR = LocalDate.now().minusYears(1);
    public static final LocalDate NEXTYEAR = LocalDate.now().plusYears(1);

    /**
     * Horse Data
     */

    public static HorseDataDto getValidHorseSimple() {
        return new HorseDataDto(
                "ValidHorseSimple",
                null,
                LocalDate.of(2000,1,1),
                Sex.Male,
                null,
                null,
                null
        );
    }

    public static HorseDataDto getValidHorseComplex() {
        return new HorseDataDto(
                "ValidHorseComplex",
                "Valid description (in Limit of 255 chars).",
                TODAY,
                Sex.Female,
                null,
                null,
                null
        );
    }


    public static HorseDataDto getNameNullHorseSimple() {
        return new HorseDataDto(
                null,
                null,
                TODAY,
                Sex.Male,
                null,
                null,
                null
        );
    }

    public static HorseDataDto getDobFutureHorseSimple() {
        return new HorseDataDto(
                "dobFutureHorseSimple",
                null,
                NEXTYEAR,
                Sex.Male,
                null,
                null,
                null
        );
    }

    public static HorseQueryParamsDto getEmptyQuery() {
        return new HorseQueryParamsDto(
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    /**
     * Food Data
     */

    public static  FoodDataDto getValidFoodSimple() {
        return new FoodDataDto(
                "simpleFood",
                null,
                null
        );
    }
}
