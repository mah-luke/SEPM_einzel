package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.dto.FoodDataDto;
import at.ac.tuwien.sepm.assignment.individual.dto.FoodDto;
import at.ac.tuwien.sepm.assignment.individual.dto.FoodQueryParamsDto;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.mapper.FoodMapper;
import at.ac.tuwien.sepm.assignment.individual.service.FoodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping(path = FoodEndpoint.BASE_PATH)
public class FoodEndpoint {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String BASE_PATH = "/food";
    private final FoodService service;
    private final FoodMapper mapper;

    public FoodEndpoint(FoodService service, FoodMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    // ASK: correct way of query params mapping???
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Stream<FoodDto> allFood(FoodQueryParamsDto qparams) {
        LOGGER.info("GET {}: {}", BASE_PATH, qparams.toString());
        try {
            return service.allFood(qparams).stream().map(mapper::entityToDto);
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        } catch (NotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FoodDto getFood(@PathVariable long id) {
        LOGGER.info("GET {}/{}:", BASE_PATH, id);
        try {
            return mapper.entityToDto(service.getFood(id));
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        } catch (NotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodDto createFood(@RequestBody FoodDataDto dto) {
        LOGGER.info("POST {}: {}", BASE_PATH, dto);
        try {
            return mapper.entityToDto(service.createFood(dto));
        } catch (ServiceException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        } catch (NotFoundException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }


}
