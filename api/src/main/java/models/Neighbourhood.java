package models;

import java.io.Serializable;

public class Neighbourhood implements Serializable {
    String name;
    long population;

    public Neighbourhood(String name, long population) {
        this.name = name;
        this.population = population;
    }
}
