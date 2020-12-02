package com.Fuel.fuelservice;

import java.text.DecimalFormat;

public class SetDoubleNum {
    /**
     * @param doubleValue
     * @return a double with 2 decimals
     */
    public static double setDoubleNum(String doubleValue) {

        double doubleValue_double = Double.parseDouble(doubleValue);

        DecimalFormat df = new DecimalFormat("#.00");
        String doubleValue_String = df.format(doubleValue_double);

        double doubleValue_decimal = Double.parseDouble(doubleValue_String);

        return doubleValue_decimal;
    }
}
