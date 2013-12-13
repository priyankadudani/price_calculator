package com.imaginea.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents menu containing List of items and value meals available.
 * 
 * @author priyanka
 * 
 */
public class Menu {

    private Map<String, Item> items;

    private List<ValueMeal> valueMeals;

    public Menu() {
        items = new HashMap<String, Item>();
        valueMeals = new ArrayList<ValueMeal>();
    }

    public Map<String, Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.put(item.getItemName(), item);
    }

    public void addValueMeal(ValueMeal valueMeal) {
        valueMeals.add(valueMeal);
    }

    public List<ValueMeal> getValueMeals() {
        return valueMeals;
    }

    // docs
    public boolean containsItem(String order) {
        if (items.containsKey(order)) {
            return true;
        }
        for (ValueMeal valueMeal : valueMeals) {
            if (valueMeal.containsItem(order)) {
                return true;
            }
        }
        return false;
    }

    public Float getItemCost(String order) {
        Item item = items.get(order);
        if (item != null) {
            return item.getCost();
        }
        return Float.MAX_VALUE;
    }

}
