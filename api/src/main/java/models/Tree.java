package models;

import java.io.Serializable;

public class Tree implements Serializable {
    private final String neighbourhood;
    private final String street;
    private final String name;
    private final double diameter;

    public Tree(String neighbourhood, String street, String name, double diameter) {
        this.neighbourhood = neighbourhood;
        this.street = street;
        this.name = name;
        this.diameter = diameter;
    }


    public String getNeighbourhood() {
        return neighbourhood;
    }
    public String getStreet() {
        return street;
    }

    public String getName() {
        return name;
    }

    public double getDiameter() {
        return diameter;
    }
}
