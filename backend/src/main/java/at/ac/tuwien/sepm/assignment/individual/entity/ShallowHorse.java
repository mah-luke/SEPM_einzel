package at.ac.tuwien.sepm.assignment.individual.entity;

import at.ac.tuwien.sepm.assignment.individual.enums.Sex;

import java.sql.Date;
import java.time.LocalDate;

public class ShallowHorse {
    private long id;
    private String name;
    private String description;
    private LocalDate dob;
    private Sex sex;
    private Food food;

    public ShallowHorse(long id, String name, String description, LocalDate dob, Sex sex, Food food) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dob = dob;
        this.sex = sex;
        this.food = food;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDob() {
        return dob;
    }

    public Sex getSex() {
        return sex;
    }

    public Food getFood() {
        return food;
    }
}
