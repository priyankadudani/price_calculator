package com.imaginea.reader;

import java.io.File;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.imaginea.domain.Menu;
import com.imaginea.domain.Restaurant;
import com.imaginea.exception.AppException;
import com.imaginea.reader.csv.CSVPriceReader;

public class CSVPriceReaderTest {

    private PriceReader priceReader;

    @BeforeClass
    public void init() {
        priceReader = new CSVPriceReader();
    }
    
    @Test
    public void testReadInput() throws AppException {

        File file = new File("data.csv");
        Map<Integer, Restaurant> restaurantDetails = priceReader.readInputFile(file);
        
        //validate Restaurants processed.
       assert restaurantDetails.size() == 6;
       
       // validate items processed.
       Menu menu1 = restaurantDetails.get(1).getMenu();
       assert menu1.getItems().size() == 2;
       
       // validate value meals processed.
       Menu menu6 = restaurantDetails.get(6).getMenu();
       assert menu6.getValueMeals().size() == 1;
    }
    
}
