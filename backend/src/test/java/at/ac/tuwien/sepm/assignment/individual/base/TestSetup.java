package at.ac.tuwien.sepm.assignment.individual.base;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseQueryParamsDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@Component
@SpringBootTest
@ActiveProfiles("test")
public class TestSetup {

    @Autowired
    HorseDao horseDao;



    /**
     * Tested Database access methods:
     */

    public Horse addHorseToDatabase(HorseDataDto dto) {
        return horseDao.createHorse(dto);
    }

    public List<Horse> getHorsesFromDatabase(HorseQueryParamsDto qParams) {
        return horseDao.getAll(qParams);
    }

    public List<Horse> getHorsesFromDatabase() {
        return getHorsesFromDatabase(TestData.getEmptyQuery());
    }

    /**
     * Comparison
     */

    public boolean entityDerivedFromDto(Horse entity, HorseDataDto dto) {
        return entity.getName().equals(dto.name())
                && entity.getSex().equals(dto.sex())
                && entity.getDob().equals(dto.dob())
                && entity.getDescription() == null ? dto.description() == null : entity.getDescription().equals(dto.description())
                && entity.getFoodId() == null ? dto.foodId() == null : entity.getFoodId().equals(dto.foodId())
                && entity.getFatherId() == null ? dto.fatherId() == null : entity.getFatherId().equals(dto.fatherId())
                && entity.getMotherId() == null ? dto.motherId() == null : entity.getMotherId().equals(dto.motherId());
    }

    public boolean dtoDerivedFromDataDto(HorseDto dto, HorseDataDto dataDto){
        return dto.name().equals(dataDto.name())
                && dto.sex().equals(dataDto.sex())
                && dto.dob().equals(dataDto.dob())
                && dto.description() == null ? dataDto.description() == null : dto.description().equals(dataDto.description())
                && dto.food() == null ? dataDto.foodId() == null : dto.food().id() == dataDto.foodId()
                && dto.father() == null ? dataDto.fatherId() == null : dto.father().id() == dataDto.fatherId()
                && dto.mother() == null ? dataDto.motherId() == null : dto.mother().id() == dataDto.motherId();
    }
}
