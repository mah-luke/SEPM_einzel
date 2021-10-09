package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDataDto;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    @ResponseStatus(HttpStatus.OK)
    public Stream<HorseDto> allHorses() {
        LOGGER.info("GET {}:", BASE_PATH);
        try {
            return service.allHorses().stream().map(mapper::entityToDto);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HorseDto getHorse(@PathVariable long id) {
        LOGGER.info("GET {}/{}:", BASE_PATH, id);
        try {
            return mapper.entityToDto(service.getHorse(id));
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HorseDto createHorse(@RequestBody HorseDataDto dto) {
        LOGGER.info("POST {}: {}", BASE_PATH, dto);
        try {
            return mapper.entityToDto(service.createHorse(dto));
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HorseDto editHorse(@PathVariable Long id, @RequestBody HorseDataDto dto) {
        LOGGER.info("PUT {}/{}: {}", BASE_PATH, id, dto);
        try {
            return mapper.entityToDto(service.editHorse(id, dto));
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HorseDto deleteHorse(@PathVariable Long id) {
        LOGGER.info("DELETE {}/{}:", BASE_PATH, id);
        try {
            return mapper.entityToDto(service.deleteHorse(id));
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
}
