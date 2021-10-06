package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = HorseEndpoint.BASE_PATH)
public class HorseEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    static final String BASE_PATH = "/horses";
    private final HorseService service;
    private final HorseMapper mapper;

    public HorseEndpoint(HorseService service, HorseMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public Stream<HorseDto> allHorses() {
        LOGGER.info("GET {}:", BASE_PATH);

        return service.allHorses().stream().map(mapper::entityToDto);
    }

    @PostMapping
    public HorseDto createHorse(@RequestBody HorseDto horseDto) {
        LOGGER.info("POST {}: {}", BASE_PATH, horseDto);
        return mapper.entityToDto(service.createHorse(horseDto));
    }

    @PutMapping
    public HorseDto editHorse(@RequestBody HorseDto horseDto) {
        LOGGER.info("PUT {}: {}", BASE_PATH, horseDto);
        return mapper.entityToDto(service.editHorse(horseDto));
    }

    @DeleteMapping("/{id}")
    public HorseDto deleteHorse(@PathVariable Long id) {
        LOGGER.info("DELETE {}/{}:", BASE_PATH, id);
        return mapper.entityToDto(service.deleteHorse(id));
    }
}
