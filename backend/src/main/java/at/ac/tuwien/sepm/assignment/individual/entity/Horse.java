package at.ac.tuwien.sepm.assignment.individual.entity;

import at.ac.tuwien.sepm.assignment.individual.enums.Sex;
import java.sql.Date;

public class Horse {
    private long id;
    private String name;
    private String description;
    private Date dob;
    private Sex sex;
    private Food food;
    private ShallowHorse father;
    private ShallowHorse mother;

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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public ShallowHorse getFather() {
        return father;
    }

    public void setFather(ShallowHorse father) {
        this.father = father;
    }

    public ShallowHorse getMother() {
        return mother;
    }

    public void setMother(ShallowHorse mother) {
        this.mother = mother;
    }
}
