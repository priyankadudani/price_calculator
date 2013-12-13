package com.imaginea.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.imaginea.domain.OutputRestaurant;
import com.imaginea.domain.Restaurant;
import com.imaginea.exception.AppException;
import com.imaginea.reader.PriceReader;
import com.imaginea.reader.csv.CSVPriceReader;
import com.imaginea.service.PricingServiceImpl;

public class PriceCalculator {

    private static PricingServiceImpl pricingService = new PricingServiceImpl();

    private static PriceReader priceReader = new CSVPriceReader();

    public static void main(String[] args) {

        try {
            // validate input arguments
            validateInputFormat(args);

            // Process files to get restaurant Details
            Map<Integer, Restaurant> restaurantDetails = priceReader.readInputFile(new File(args[0]));
            
            // process ordered items to calculate order cost
            List<String> orderItemList = new ArrayList<String>();
            for (int i = 1; i < args.length; i++) {
                orderItemList.add(args[i]);
            }
            OutputRestaurant output = pricingService.calculateOrderCost(restaurantDetails, orderItemList);

            
            printDesiredResult(output);

        } catch (Exception e) {
            System.err.println("Error occured while calculating cost: " + e);
        }
    }

    private static void printDesiredResult(OutputRestaurant output) {
        System.out.println();
        System.out.println("*********************************");
        System.out.println("output");
        System.out.println();
        if (output != null) {
            System.out.println(output);
        } else {
            System.out.println("no matching restaurant could be found");
        }
        System.out.println();
        System.out.println("***********************************");
        System.out.println();
    }

    private static void validateInputFormat(String[] args) throws AppException {
        if (args.length < 1) {
            throw new AppException("No data file given!");
        }
        if (args.length < 2) {
            throw new AppException("No order to process!");
        }
        if("".equals(args[0]) || "".equals(args[1])){
            throw new AppException("Invalid input!");
        }
    }

}
