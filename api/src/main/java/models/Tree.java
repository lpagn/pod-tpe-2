package models;

import java.io.Serializable;

public class Tree implements Serializable {
    String neighbourhood;
    String street;
    String name;
    double diameter;

    public Tree(String neighbourhood, String street, String name, double diameter) {
        this.neighbourhood = neighbourhood;
        this.street = street;
        this.name = name;
        this.diameter = diameter;
    }


    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }
}
