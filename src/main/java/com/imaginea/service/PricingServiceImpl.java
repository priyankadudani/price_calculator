package com.imaginea.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.imaginea.domain.MealCombo;
import com.imaginea.domain.Menu;
import com.imaginea.domain.OutputRestaurant;
import com.imaginea.domain.Restaurant;
import com.imaginea.domain.ValueMeal;
import com.imaginea.util.PricingHelper;

/**
 * Service to calculate price.
 * Uses dynamic programming based algorithm to calculate minimum cost.
 * 
 * @author priyanka
 * 
 */
public class PricingServiceImpl implements PricingService {

    public OutputRestaurant calculateOrderCost(Map<Integer, Restaurant> restaurantDetails, List<String> orderList) {

        Float globalMinCost = Float.MAX_VALUE;
        int selectedRestaurant = -1;
        Collections.sort(orderList);
        for (int restaurantId : restaurantDetails.keySet()) {
            Menu menu = restaurantDetails.get(restaurantId).getMenu();

            // Process only if restaurant has all the ordered items
            if (isAllItemPresentInMenu(orderList, menu)) {
                Float minCostPerRestaurant = findMinCostPerRestaurant(menu, orderList);
                if (minCostPerRestaurant < globalMinCost) {
                    globalMinCost = minCostPerRestaurant;
                    selectedRestaurant = restaurantId;
                }
            }
        }

        if (selectedRestaurant == -1) {
            return null;
        } else {
            return new OutputRestaurant(selectedRestaurant, globalMinCost);
        }
    }

    /**
     * Checks if all the ordered items are present in menu.
     * 
     * @param orderList
     * @param menu
     * @return true if all the ordered items are present in menu, else returns false.
     */
    private Boolean isAllItemPresentInMenu(List<String> orderList, Menu menu) {
        for (String order : orderList) {
            if (!menu.containsItem(order)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calculates minimum cost per Restaurant considering all the value meals available in the menu.
     * 
     * Solution uses dynamic programming to calculate minimum cost while considering all combinations of
     * values meal and individual items. The solution is built from bottom to top by starting from problem
     * size of 1 (i.e. single item at a time) to n , size of order list. At any level k optimal cost is s
     * calculated as minimum of following two options:
     * a) minimum of all the smaller subproblems (1 to k-1)
     * b) Value meal containing all k desired items
     * 
     * final level of this data structure gives the minimum cost for the order.
     * 
     * Complexity of algorithm is O(n^2 log n), n being size of the order made by user. 
     * 
     * @param menu
     * @param orderList
     * @return minimum cost for ordered items.
     */
    private static Float findMinCostPerRestaurant(Menu menu, List<String> orderList) {

        // initialize table with all possible combinations of orderlist
        Map<Integer, Map<MealCombo, Float>> optimalCostMap = getAllCombination(orderList);

        // base case for level 1 - update costs for individual items
        Map<MealCombo, Float> currLevelProblems = optimalCostMap.get(1);

        Float minCost;
        for (MealCombo item : currLevelProblems.keySet()) {
            minCost = menu.getItemCost(item.getMealName());

            // calculate cost for the items only present in value meals
            for (ValueMeal valueMeal : menu.getValueMeals()) {
                if (valueMeal.getCost() < minCost && PricingHelper.containsAllItem(valueMeal, item)) {
                    minCost = valueMeal.getCost();
                }
            }
            currLevelProblems.put(item, minCost);
        }

        // calculate minimum costs based on subproblems.
        minCost = Float.MAX_VALUE;
        for (int level = 2; level <= orderList.size(); level++) {  // this loop runs log(n) times, covering all the levels 

            currLevelProblems = optimalCostMap.get(level);
            for (MealCombo meal : currLevelProblems.keySet()) {
                minCost = Float.MAX_VALUE;

                // Use all the smaller subproblems to solve current problem
                for (int i = level - 1; i > 0; i--) { // this loop runs log(n) times 
                    
                    for (MealCombo subMeal : optimalCostMap.get(i).keySet()) {
                        if (meal.isSubProblem(subMeal)) {

                            // divide the problem in 2 smaller problems - subMeal and remainingMeal
                            // cost will be sum of the optimal cost of the two sub problems
                            MealCombo remainingMeal = meal.substract(subMeal);
                            Float cost = optimalCostMap.get(subMeal.getLevel()).get(subMeal);
                            cost = cost + optimalCostMap.get(remainingMeal.getLevel()).get(remainingMeal);
                            if (minCost > cost) {
                                minCost = cost;
                            }
                        }
                    }
                }

                for (ValueMeal valueMeal : menu.getValueMeals()) {
                    // update if value meal cost is actually minimum
                    // and it has all the items required in this problem
                    if (valueMeal.getCost() < minCost && PricingHelper.containsAllItem(valueMeal, meal)) {
                        minCost = valueMeal.getCost();
                    }
                }
                currLevelProblems.put(meal, minCost);
            }
        }

        // return the cost of final level problem, which is original order list.
        for (MealCombo meal : optimalCostMap.get(orderList.size()).keySet()) {
            return optimalCostMap.get(orderList.size()).get(meal);
        }
        return null;
    }

    /**
     * calculates all the possible combination of given orderList.
     * 
     * @param orderList
     * @return costs map with all the combinations of order items initialized with maximum cost.
     */
    public static Map<Integer, Map<MealCombo, Float>> getAllCombination(List<String> orderList) {

        // Map containing combo meals for every level
        Map<Integer, Map<MealCombo, Float>> optimalCostMap = new HashMap<Integer, Map<MealCombo, Float>>();

        getCombinationsUtil(orderList, optimalCostMap, new MealCombo(""), 0);
        return optimalCostMap;
    }

    /**
     * Recursively called to calculate all the possible combination of given orderList.
     * Also Initializes the cost values with maximum value.
     * 
     * @param orderList
     * @param optimalCostMap
     * @param previous
     * @param idx
     */
    private static void getCombinationsUtil(List<String> orderList, Map<Integer, Map<MealCombo, Float>> optimalCostMap, MealCombo previous, int idx) {

        for (int i = idx; i < orderList.size(); i++) {
            MealCombo currMeal = new MealCombo(previous, orderList.get(i));
            Map<MealCombo, Float> map = optimalCostMap.get(currMeal.getLevel());
            if (null == map) {
                map = new HashMap<MealCombo, Float>();
                optimalCostMap.put(currMeal.getLevel(), map);
            }

            if (!map.containsKey(currMeal)) {
                map.put(currMeal, Float.MAX_VALUE); // initialize cost with maximum value.
                getCombinationsUtil(orderList, optimalCostMap, currMeal, i + 1);
            }
        }

    }

}
