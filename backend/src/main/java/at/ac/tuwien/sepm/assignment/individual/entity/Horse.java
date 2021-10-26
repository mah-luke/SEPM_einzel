package at.ac.tuwien.sepm.assignment.individual.entity;

import at.ac.tuwien.sepm.assignment.individual.enums.Sex;
import java.time.LocalDate;
import java.util.Objects;

public class Horse {
    private long id;
    private String name;
    private String description;
    private LocalDate dob;
    private Sex sex;
    private Long foodId;
    private Long fatherId;
    private Long motherId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public Long getFatherId() {
        return fatherId;
    }

    public void setFatherId(Long fatherId) {
        this.fatherId = fatherId;
    }

    public Long getMotherId() {
        return motherId;
    }

    public void setMotherId(Long motherId) {
        this.motherId = motherId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Horse horse = (Horse) o;
        return id == horse.id && name.equals(horse.name) && Objects.equals(description, horse.description) && dob.equals(horse.dob) && sex == horse.sex && Objects.equals(foodId, horse.foodId) && Objects.equals(fatherId, horse.fatherId) && Objects.equals(motherId, horse.motherId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, dob, sex, foodId, fatherId, motherId);
    }
}
