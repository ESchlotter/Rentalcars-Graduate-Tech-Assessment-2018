package com.Rentalcars;

import java.util.Comparator;

/**
 * Created by Eduard Schlotter on 07/01/2018.
 */

public class Vehicle {
    private String sipp, name, supplier;
    private float price, supplierRating;
    private float vehicleScore = 0;
    private float totalScore = 0;
    private String carType, doors, transmission, airCon, fuel;

    // Arrays with specs info
    private String[][] carTypesList = {
            {"M","Mini"},
            {"E","Economy"},
            {"C","Compact"},
            {"I","Intermediate"},
            {"S","Standard"},
            {"F","Full size"},
            {"P","Premium"},
            {"L","Luxury"},
            {"X","Special"}
    };
    private String[][] doorList = {
            {"B","2 Doors"},
            {"C","4 Doors"},
            {"D","5 Doors"},
            {"W","Estate"},
            {"T","Convertible"},
            {"F","SUV"},
            {"P","Pick up"},
            {"V","Passenger Van"}
    };
    private String[][] transmissionList = {
            {"M","Manual"},
            {"A","Automatic"}
    };
    private String[][] airConList = {
            {"N","No Air Conditioning"},
            {"R","Air Conditioning"}
    };

    public Vehicle(String sipp, String name, float price, String supplier, float supplierRating){
        this.sipp = sipp;
        this.name = name;
        this.price = price;
        this.supplier = supplier;
        this.supplierRating = supplierRating;

        // Get Specs
        carType = findItem(carTypesList, sipp.charAt(0));
        doors = findItem(doorList, sipp.charAt(1));
        if(doors == null)
            doors = findItem(carTypesList, sipp.charAt(1)); // if null then search car type
        transmission = findItem(transmissionList, sipp.charAt(2));
        airCon = findItem(airConList, sipp.charAt(3));
        // Fuel
        if (airCon != null) fuel = "Petrol"; // Petrol on both cases
        else fuel = null; // In case of error

        // Calculate score
        if(airCon == "Air Conditioning") vehicleScore += 2;
        if(transmission == "Automatic") vehicleScore += 5;
        else if (transmission == "Manual") vehicleScore += 1; // if other then error (0)

        totalScore = vehicleScore + supplierRating; // add to totalscore

    }

    private static String findItem (String[][] arrayOfItems, char letter){
        for(String[] array : arrayOfItems){ // search for letter
            if(array[0].charAt(0) == letter){
                return array[1]; // return item string
            }
        }
        // If letter not in array then return null
        return null;
    }

    public String getCarType() {
        return carType;
    }

    public String getDoors() {
        return doors;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getAirCon() {
        return airCon;
    }

    public String getFuel() {
        return fuel;
    }

    public int getVehicleScore() {
        return (int)vehicleScore;
    }

    public Float getTotalScore() {
        return totalScore;
    }

    public String getSipp() {
        return sipp;
    }

    public String getName() {
        return name;
    }

    public String getSupplier() {
        return supplier;
    }

    public float getPrice() {
        return price;
    }

    public float getSupplierRating() {
        return supplierRating;
    }

    /*Comparator for sorting the list by Price*/
    public static Comparator<Vehicle> VehiclePriceComparator = (v1, v2) -> {
        float VehiclePrice1 = v1.getPrice();
        float VehiclePrice2 = v2.getPrice();

        // Ascending order
        return Float.compare(VehiclePrice1, VehiclePrice2);
    };

    /*Comparator for sorting the list by Supplier Rating*/
    public static Comparator<Vehicle> VehicleSupplierComparator = (v1, v2) -> {
        float VehiclePrice1 = v1.getSupplierRating();
        float VehiclePrice2 = v2.getSupplierRating();

        // Descending order
        return Float.compare(VehiclePrice2, VehiclePrice1);
    };

    /*Comparator for sorting the list by Total Score*/
    public static Comparator<Vehicle> VehicleScoreComparator = (v1, v2) -> {
        float VehiclePrice1 = v1.getTotalScore();
        float VehiclePrice2 = v2.getTotalScore();

        // Descending order
        return Float.compare(VehiclePrice2, VehiclePrice1);
    };
}
