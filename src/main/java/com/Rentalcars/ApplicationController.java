package com.Rentalcars;

/**
 * Created by Eduard Schlotter on 06/01/2018.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApplicationController {
    @Autowired
    private Environment env;
    private ArrayList<Vehicle> vehicles;
    private ArrayList<Vehicle> ratedSupplierVehicles;

    public ApplicationController() throws IOException, JSONException {
        JSONObject json = readJsonFromUrl("http://www.rentalcars.com/js/vehicles.json"); // get JSONObject from URL
        JSONArray vehicleList = findVehicleList(json);
        vehicles = new ArrayList<>();

        // Populate Vehicle ArrayList
        for(int i = 0; i < vehicleList.length(); i++){
            // Get values from JSONObject
            String sipp = vehicleList.getJSONObject(i).getString("sipp");
            String name = vehicleList.getJSONObject(i).getString("name");
            float price = vehicleList.getJSONObject(i).getFloat("price");
            String supplier = vehicleList.getJSONObject(i).getString("supplier");
            float rating = vehicleList.getJSONObject(i).getFloat("rating");
            vehicles.add(new Vehicle(sipp, name, price, supplier, rating)); // add to list
        }

        // Populated Rated Supplier Vehicles by car type
        ratedSupplierVehicles = new ArrayList<>();
        for(Vehicle vehicle: vehicles){
            boolean found = false; // to execute code after loops
            for(int i = 0; i < ratedSupplierVehicles.size(); i++){
                Vehicle ratedVehicle = ratedSupplierVehicles.get(i);
                if(vehicle.getCarType() == ratedVehicle.getCarType() ){
                    if(vehicle.getSupplierRating() > ratedVehicle.getSupplierRating()){
                        ratedSupplierVehicles.remove(i); //remove old vehicle for car type
                        ratedSupplierVehicles.add(vehicle); // add new vehicle for car type
                    }
                    found = true; // avoids adding vehicle again at the end
                    break;
                }
            }
            if(!found){
                ratedSupplierVehicles.add(vehicle); // no vehicle found - adds to list
            }
        }
    }

    /** Get Vehicle List From Object
     *
     * @param json
     * @return
     */
    private JSONArray findVehicleList(JSONObject json){
        return json.getJSONObject("Search").getJSONArray("VehicleList"); // get vehicle list
    }

    /** Reads entire text and turns to String
     *
     * @param text file to read
     * @return Text in form of string
     * @throws IOException
     */
    private String readAll(Reader text) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        int pointer;
        while ((pointer = text.read()) != -1) {
            stringBuilder.append((char) pointer); // add to stringBuilder
        }
        return stringBuilder.toString(); //return as String
    }

    /** Gets text from URL and turns into JSONObject
     *
     * @param url Where to retrieve JSON from
     * @return JSONObject from URL
     * @throws IOException
     * @throws JSONException
     */
    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd); // read entire text
            JSONObject json = new JSONObject(jsonText); // turn to JSONobject
            return json;
        } finally {
            is.close(); // close stream
        }
    }

    /* REST API mappings - could also be displayed through JSON */
    @RequestMapping("/prices")
    public @ResponseBody
    String prices(){
        Collections.sort(vehicles, Vehicle.VehiclePriceComparator); // Sort Vehicle by Price
        String printable = ""; // Final String displayed through API
        printable += "Ascending Price Listing <br> <br> \n";
        for(Vehicle v: vehicles){
            printable += v.getName() + " - " + v.getPrice() + " <br> \n";
        }
        System.out.println(printable);
        return printable;
    }

    @RequestMapping("/specs")
    public @ResponseBody
    String specs(){
        String printable = "";
        printable += "Vehicle Specification List <br> <br> \n";
        for(Vehicle v: vehicles){
            printable += v.getName() + " - " + v.getSipp() + " - " + v.getCarType() + " - " +
                         v.getDoors() + " - " + v.getTransmission() + " - " +
                         v.getFuel() + " - " + v.getAirCon() + " <br> \n";
        }
        System.out.println(printable);
        return printable;
    }

    @RequestMapping("/ratings")
    public @ResponseBody
    String ratings(){
        Collections.sort(ratedSupplierVehicles, Vehicle.VehicleSupplierComparator); // sort vehicles by supplier rating
        String printable = "";
        printable += "Highest Rated Suppliers <br> <br> \n";
        for(Vehicle v: ratedSupplierVehicles) {
            printable += v.getName() + " - " + v.getCarType() + " - " +
                         v.getSupplier() + " - " + v.getSupplierRating() + " <br> \n" ;
        }
        System.out.println(printable);
        return printable;
    }

    @RequestMapping("/scores")
    public @ResponseBody
    String scores(){
        Collections.sort(vehicles, Vehicle.VehicleScoreComparator); // sort vehicles by totalScore
        String printable = "";
        printable += "Highest Scoring Vehicles <br> <br> \n";
        for(Vehicle v: vehicles) {
            printable += v.getName() + " - " + v.getVehicleScore() + " - " +
                    v.getSupplierRating() + " - " + v.getTotalScore() + " <br> \n";
        }
        System.out.println(printable);
        return printable;
    }

}
