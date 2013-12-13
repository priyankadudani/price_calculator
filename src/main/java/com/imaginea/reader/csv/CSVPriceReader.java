package com.imaginea.reader.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;

import com.imaginea.domain.Item;
import com.imaginea.domain.Menu;
import com.imaginea.domain.Restaurant;
import com.imaginea.domain.ValueMeal;
import com.imaginea.exception.AppException;
import com.imaginea.reader.PriceReader;
import com.imaginea.util.PricingConstants;

import static com.imaginea.util.PricingConstants.CSV_EXTENSION;

/**
 * CSV File reader
 * 
 * @author priyanka
 * 
 */
public class CSVPriceReader implements PriceReader {

    /**
     * reads input csv file and creates restaurant Object and its menu for all unique restaurant ids.
     * 
     */
    public Map<Integer, Restaurant> readInputFile(File file) throws AppException {

        validateCSVFile(file);

        CSVReader csvReader = null;
        Map<Integer, Restaurant> restaurantDetails = null;

        try {
           csvReader = new CSVReader(new FileReader(file));
           restaurantDetails = processInput(csvReader);

        } catch (FileNotFoundException e) {
            throw new AppException("File does not exists.", e);

        } catch (IOException e) {
            throw new AppException("Error while processing input File.", e);

        } finally {
            try {
                if (csvReader != null) {
                    csvReader.close();
                }
            } catch (IOException e) {
                throw new AppException("Error while closing csv reader", e);
            }
        }

        return restaurantDetails;
    }

    /**
     * Processes each record of file to create item or value-meal, except records having less than 3 columns.
     * Also, leading and trailing spaces with item names present in file gets trimmed when storing an item.
     * throws exception in case of negative cost.
     * 
     * @param csvReader
     * @return Map of all Restaurant object
     * @throws IOException
     * @throws AppException
     */
    private Map<Integer, Restaurant> processInput(CSVReader csvReader) throws IOException, AppException {
        Map<Integer, Restaurant> restaurantDetails = new HashMap<Integer, Restaurant>();
        String[] line = csvReader.readNext();
        while (line != null) {

            if (line.length < 3) {
                // ignore line
                // throw new AppException("Invalid format in data file");
            } else {

                int id = Integer.valueOf(line[0]);
                if (!restaurantDetails.containsKey(id)) {
                    restaurantDetails.put(id, new Restaurant(id, new Menu()));
                }
                Menu menu = restaurantDetails.get(id).getMenu();
                Float cost = Float.valueOf(line[1]);
                if (cost <= 0) {
                    throw new AppException("Cost is not valid.");
                }
                if (line.length == 3) {
                    menu.addItem(new Item(line[2].trim(), cost));
                } else {
                    List<String> mealItems = new ArrayList<String>();
                    for (int i = 3; i < line.length; i++) {
                        mealItems.add(line[i].trim());
                    }
                    menu.addValueMeal(new ValueMeal(mealItems, cost));
                }
            }
            line = csvReader.readNext();
        }
        return restaurantDetails;
    }

    /**
     * validates file and throws exception if file format does not match.
     * 
     * @param fileName
     * @throws AppException
     */
    private void validateCSVFile(File file) throws AppException {
        if (null == file) {
            throw new AppException("File does not exist.");
        }
        String fileName = file.getName();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if (extension == null) {
            throw new AppException("Invalid file name");
        }
        if (!CSV_EXTENSION.equals(extension)) {
            throw new AppException("File extension should be " + CSV_EXTENSION);
        }

    }

}
