package com.imaginea.domain;

/**
 * Preferred Restaurant details.
 * 
 * @author priyanka
 * 
 */
public class OutputRestaurant {

    private int id;
    private Float cost;

    public OutputRestaurant(int id, Float cost) {
        this.id = id;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public Float getCost() {
        return cost;
    }
    
    @Override
    public String toString() {
        String str = this.id + ", ";
        str = str + String.format("%.2f", this.cost);
        return str;
    }

}
