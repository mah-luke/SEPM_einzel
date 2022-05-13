package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseQueryParamsDto;
import at.ac.tuwien.sepm.assignment.individual.exception.RestException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = HorseEndpoint.BASE_PATH)
public class HorseEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_PATH = "/horses";
    private final HorseService service;
    private final HorseMapper mapper;

    public HorseEndpoint(HorseService service, HorseMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Stream<HorseDto> allHorses(HorseQueryParamsDto qParams) throws RestException {
        LOGGER.info("GET {}: {}", BASE_PATH, qParams);
        try {
            return service.allHorses(qParams).stream().map(mapper::entityToDto);
        } catch (ServiceException e) {
            throw new RestException(e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HorseDto getHorse(@PathVariable long id) throws RestException {
        LOGGER.info("GET {}/{}:", BASE_PATH, id);
        try {
            return mapper.entityToDto(service.getHorse(id));
        } catch (ServiceException e) {
            throw new RestException(e.getMessage(), e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HorseDto createHorse(@RequestBody HorseDataDto dto) throws RestException {
        LOGGER.info("POST {}: {}", BASE_PATH, dto);
        try {
            return mapper.entityToDto(service.createHorse(dto));
        } catch (ServiceException e) {
            throw new RestException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HorseDto editHorse(@PathVariable long id, @RequestBody HorseDataDto dto) throws RestException {
        LOGGER.info("PUT {}/{}: {}", BASE_PATH, id, dto);
        try {
            return mapper.entityToDto(service.editHorse(id, dto));
        } catch (ServiceException e) {
            throw new RestException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHorse(@PathVariable long id) throws RestException {
        LOGGER.info("DELETE {}/{}:", BASE_PATH, id);
        try {
            service.deleteHorse(id);
        } catch (ServiceException e) {
            throw new RestException(e.getMessage(), e);
        }
    }
}
