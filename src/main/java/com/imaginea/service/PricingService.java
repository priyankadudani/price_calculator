package com.imaginea.service;

import java.util.List;
import java.util.Map;

import com.imaginea.domain.OutputRestaurant;
import com.imaginea.domain.Restaurant;

public interface PricingService {

    OutputRestaurant calculateOrderCost(Map<Integer, Restaurant> restaurantDetails, List<String> orderList);

}
