package at.ac.tuwien.sepm.assignment.individual.service;

public interface ModelValidator<Type> extends Validator<Type> {
    /**
     * Validates an id corresponding to a Type Object for a Model e.g. for a Horse or Food.
     *
     * @param id the id to validate.
     * @return the validated id if valid
     */
    long validate(long id);
}
