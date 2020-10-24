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
}
