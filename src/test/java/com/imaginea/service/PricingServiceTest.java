package com.imaginea.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.imaginea.domain.Item;
import com.imaginea.domain.Menu;
import com.imaginea.domain.OutputRestaurant;
import com.imaginea.domain.Restaurant;
import com.imaginea.domain.ValueMeal;

public class PricingServiceTest {

    PricingServiceImpl pricingService;

    private Map<Integer, Restaurant> restaurantDetails;

    @BeforeClass
    public void init() {
        
        pricingService = new PricingServiceImpl();
        
        restaurantDetails = new HashMap<Integer, Restaurant>();

        Menu menu1 = new Menu();
        menu1.addItem(new Item("burger", new Float(4.00)));
        menu1.addItem(new Item("tofu_log", new Float(8.00)));
        restaurantDetails.put(1, new Restaurant(1,menu1));

        Menu menu2 = new Menu();
        menu2.addItem(new Item("burger", new Float(5.00)));
        menu2.addItem(new Item("tofu_log", new Float(6.50)));
        restaurantDetails.put(2, new Restaurant(2, menu2));

        Menu menu3 = new Menu();
        menu3.addItem(new Item("extreme_fajita", new Float(4.0)));
        menu3.addItem(new Item("fancy_european_water", new Float(8.00)));
        restaurantDetails.put(3, new Restaurant(3,menu3));

        Menu menu4 = new Menu();
        menu4.addItem(new Item("fancy_european_water", new Float(5.00)));
        List<String> mealItems = new ArrayList<String>();
        mealItems.add("extreme_fajita");
        mealItems.add("jalapeno_poppers");
        mealItems.add("extra_salsa");
        menu4.addValueMeal(new ValueMeal(mealItems, new Float(6.00)));
        restaurantDetails.put(4, new Restaurant(4, menu4));

    }

    @Test
    public void testForInvalidMenuItem() {
        List<String> orderList = new ArrayList<String>();
        orderList.add("paneer_burger");
        OutputRestaurant output = pricingService.calculateOrderCost(restaurantDetails, orderList);
        assert output == null;
    }

    @Test
    public void testMinimumCost() {
        List<String> orderList = new ArrayList<String>();
        orderList.add("burger");
        OutputRestaurant output = pricingService.calculateOrderCost(restaurantDetails, orderList);
        assert output.getId() == 1;
        assert output.getCost().equals(new Float("4.00"));
    }

    @Test
    public void testCostWithValueMealIncluded() {
        List<String> orderList = new ArrayList<String>();
        orderList.add("fancy_european_water");
        orderList.add("extreme_fajita");
        OutputRestaurant output = pricingService.calculateOrderCost(restaurantDetails, orderList);
        assert output.getId() == 4;
        assert output.getCost().equals(new Float("11.00"));
    }
    
    @Test
    public void testCostWithSmallerValueMealsHavingMinimumCost( ) {
    
        List<String> orderList = new ArrayList<String>();
        orderList.add("A");
        orderList.add("B");
        orderList.add("C");
        Map<Integer, Restaurant> details = getMenuWithManyValueMeals();
        OutputRestaurant output = pricingService.calculateOrderCost(details, orderList);
        assert output.getId() == 1;
        assert output.getCost().equals(new Float("23.00"));
    }
    
    @Test
    public void testCostWithLargestValueMealsHavingMinimumCost() {
        
        List<String> orderList = new ArrayList<String>();
        orderList.add("A");
        orderList.add("B");
        orderList.add("C");
        orderList.add("E");
        Map<Integer, Restaurant> details = getMenuWithManyValueMeals();
        OutputRestaurant output = pricingService.calculateOrderCost(details , orderList);
        assert output.getId() == 1;
        assert output.getCost().equals(new Float("25.00"));
    }
    
    @Test
    public void testCostExcludingLargestValueMeal(){
        Map<Integer, Restaurant> details = getMenuWithManyValueMeals();
        Menu menu1 = new Menu();
        menu1.addItem(new Item("A", new Float(50.00)));
        menu1.addItem(new Item("B", new Float(50.00)));
        menu1.addItem(new Item("C", new Float(50.00)));
        menu1.addItem(new Item("D", new Float(50.00)));
        menu1.addItem(new Item("E", new Float(50.00)));
        
        List<String> meals1 = new ArrayList<String>();
        meals1.add("A");
        meals1.add("B");
        meals1.add("C");
        meals1.add("D");
        ValueMeal valueMeal1 = new ValueMeal(meals1, new Float(20.00));
        
        List<String> meals2 = new ArrayList<String>();
        meals2.add("A");
        meals2.add("B");
        meals2.add("C");
        ValueMeal valueMeal2 = new ValueMeal(meals2, new Float(15.00));
        
        List<String> meals3 = new ArrayList<String>();
        meals3.add("D");
        meals3.add("E");
        ValueMeal valueMeal3 = new ValueMeal(meals3, new Float(20.00));

        menu1.addValueMeal(valueMeal1);
        menu1.addValueMeal(valueMeal2);
        menu1.addValueMeal(valueMeal3);
        details.put(1, new Restaurant(1, menu1));
        
        List<String> orderList = new ArrayList<String>();
        orderList.add("A");
        orderList.add("B");
        orderList.add("C");
        orderList.add("D");
        orderList.add("E");
        
        OutputRestaurant output = pricingService.calculateOrderCost(details , orderList);
        assert output.getId() == 1;
        assert output.getCost().equals(new Float("35.00"));
    }
    
    public Map<Integer, Restaurant>  getMenuWithManyValueMeals() {
        
        Map<Integer, Restaurant> restaurantDetails = new HashMap<Integer, Restaurant>();

        Menu menu1 = new Menu();
        menu1.addItem(new Item("A", new Float(4.00)));
        menu1.addItem(new Item("B", new Float(8.00)));
        menu1.addItem(new Item("C", new Float(20.00)));
        menu1.addItem(new Item("D", new Float(25.00)));
        menu1.addItem(new Item("E", new Float(10.00)));
        List<String> meals1 = new ArrayList<String>();
        meals1.add("A");
        meals1.add("B");
        ValueMeal valueMeal1 = new ValueMeal(meals1, new Float(10.00));

        List<String> meals2 = new ArrayList<String>();
        meals2.add("A");
        meals2.add("C");
        ValueMeal valueMeal2 = new ValueMeal(meals2, new Float(15.00));
        
        List<String> meals3 = new ArrayList<String>();
        meals3.add("A");
        meals3.add("B");
        meals3.add("C");
        meals3.add("E");
        ValueMeal valueMeal3 = new ValueMeal(meals3, new Float(25.00));

        menu1.addValueMeal(valueMeal1);
        menu1.addValueMeal(valueMeal2);
        menu1.addValueMeal(valueMeal3);
        restaurantDetails.put(1, new Restaurant(1, menu1));
        
        return restaurantDetails;
    }
    

}
