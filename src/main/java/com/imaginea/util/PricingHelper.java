package com.imaginea.util;

import java.util.List;

import com.imaginea.domain.MealCombo;
import com.imaginea.domain.ValueMeal;

/**
 * Utility class for pricing calculations.
 * 
 * @author priyanka
 * 
 */
public class PricingHelper {

    /**
     * checks if value-meal available at restaurant contains all the items asked in a meal.
     * If any of the asked item is missing, then false is returned as value-meal can not be used for this particular case.
     * 
     * @param valueMeal, offered by restaurant
     * @param meal, Meal combo being ordered by user.
     * @return false, if any item is missing from value-meal
     *         true, if all asked items are present in value-meal
     */         
    public static boolean containsAllItem(ValueMeal valueMeal, MealCombo meal) {
        if (valueMeal.getMealItems().size() < meal.getLevel()) {
            return false;
        }
        List<MealCombo> allMeals = meal.getMeals();
        for (MealCombo m : allMeals) {
            // check meal name only for base meal combos
            if (m.getLevel() == 1 && !valueMeal.containsItem(m.getMealName())) {
                return false;
            }
        }
        return true;
    }

}
