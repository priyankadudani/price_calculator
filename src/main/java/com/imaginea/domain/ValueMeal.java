package com.imaginea.domain;

import java.util.List;

/**
 * Represents group of several items, at a discounted price
 * 
 * @author priyanka
 * 
 */
public class ValueMeal {

    private List<String> mealItems;

    private Float cost;

    public ValueMeal(List<String> mealItems, Float cost) {
        this.mealItems = mealItems;
        this.cost = cost;
    }

    public List<String> getMealItems() {
        return mealItems;
    }

    public void setMealItems(List<String> mealItems) {
        this.mealItems = mealItems;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public boolean containsAnyItem(List<String> orderList) {
        for (int i = 0; i < orderList.size(); i++) {
            if (this.mealItems.contains(orderList.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean containsItem(String order) {
        return this.mealItems.contains(order);
    }

    public boolean validOrder(List<String> orderList) {
        if (this.mealItems.size() < orderList.size()) {
            return false;
        }
        return true;
    }

}
