package com.imaginea.reader;

import java.io.File;
import java.util.Map;

import com.imaginea.domain.Restaurant;
import com.imaginea.exception.AppException;

public interface PriceReader {

    /**
     * Processes given file and creates Restaurant object for all the given Restaurants.
     * 
     * @param file
     * @return Map containing Restaurant id and Restaurant details
     * @throws AppException
     */
    Map<Integer, Restaurant> readInputFile(File file) throws AppException;

}
