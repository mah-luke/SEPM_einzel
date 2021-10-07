package at.ac.tuwien.sepm.assignment.individual.validation.impl;

import at.ac.tuwien.sepm.assignment.individual.exception.IllegalArgumentException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class IdValidator implements Validator<Long> {
    @Override
    public Long validate(Long id) throws ValidationException {
        if (id == null) throw new IllegalArgumentException("Ids must never be null");
        if (id < 1) throw new IllegalArgumentException("Ids must be positive numbers. Id: " + id);

        return id;
    }
}
