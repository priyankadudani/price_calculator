package com.imaginea.reader;

import static com.imaginea.util.PricingConstants.CSV_EXTENSION;

import com.imaginea.exception.AppException;
import com.imaginea.reader.csv.CSVPriceReader;

public class PriceReaderFactory {

    public static PriceReader getInstance(String fileName) throws AppException {
        if (fileName == null) {
            throw new AppException(" Invalid file name");
        }
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if (extension == null) {
            throw new AppException(" Invalid file name");
        }
        
        PriceReader priceReader = null;
        if (CSV_EXTENSION.equals(extension)) {
            priceReader = new CSVPriceReader();
        } else {
            throw new AppException(extension + " is not supported");
        }
        return priceReader;
    }

}
