package com.imaginea.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Object used for calculations purpose to solve for minimum cost.
 * 
 * 
 * @author priyanka
 * 
 */
public class MealCombo {

    /**
     * Different items present in this meal.
     */
    private List<MealCombo> meals;
    
    /**
     * Concatenated value of all the items present in this meal combo.
     */
    private String mealName = "";

    /**
     * Represents size of the meal problems, useful when considering sub-problems.
     * 
     */
    private int level;

    public MealCombo() {
    }

    public MealCombo(String mealName) {
        this.mealName = mealName;
        this.meals = new ArrayList<MealCombo>();
        this.level = 1;
    }

    public MealCombo(MealCombo subMeal, String mealName) {
        this.meals = new ArrayList<MealCombo>(subMeal.meals.size() + 1);
        for (MealCombo meal : subMeal.meals) {
            this.meals.add(meal);
        }
        this.meals.add(new MealCombo(mealName));
        this.mealName = subMeal.mealName + mealName;
        this.level = this.meals.size();
    }

    public MealCombo(String mealName, List<MealCombo> meals) {
        this.mealName = mealName;
        this.meals = meals;
        this.level = meals.size();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getMealName() {
        return mealName;
    }

    public List<MealCombo> getMeals() {
        return meals;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mealName == null) ? 0 : mealName.hashCode());
        result = prime * result + ((meals == null) ? 0 : meals.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MealCombo other = (MealCombo) obj;
        if (mealName == null) {
            if (other.mealName != null)
                return false;
        } else if (!mealName.equals(other.mealName))
            return false;
        if (meals == null) {
            if (other.meals != null)
                return false;
        } else if (!meals.equals(other.meals))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return mealName;
    }

    /**
     * checks if this meal has all the items which are present in given meal. 
     * 
     * @param subMeal
     * @return true, if all submeal items are present in current mealCombo
     *         else return false.
     */
    public boolean isSubProblem(MealCombo subMeal) {
        if (this.getLevel() <= subMeal.getLevel()) {
            return false;
        }
        for (MealCombo meal : subMeal.meals) {
            if (!this.meals.contains(meal)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calculates the remaining meal by removing all items present in submeal-combo from current meal-combo.
     * 
     * @param subMeal
     * @return
     */
    public MealCombo substract(MealCombo subMeal) {
        String mealName = "";
        List<MealCombo> remainingMeal = new ArrayList<MealCombo>();
        for (MealCombo meal : this.meals) {
            if (!subMeal.meals.contains(meal)) {
                mealName = mealName + meal.mealName;
                remainingMeal.add(meal);
            }
        }
        return new MealCombo(mealName, remainingMeal);
    }

}
