/**
 * Created by eschlotter on 09/01/2018.
 */
import com.Rentalcars.RentalcarsApplication;
import com.Rentalcars.Vehicle;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;
public class VehicleTest {


    @Test
    public void priceListCorrectOrder() {
        ArrayList<Vehicle> testVehicles = new ArrayList<>();
        Vehicle testCar = new Vehicle("MBMX", "TestCar", 200,"Supplier1",6);
        Vehicle testCar1 = new Vehicle("MBMX", "TestCar1", 300,"Supplier2",6);
        Vehicle testCar2 = new Vehicle("MBMX", "TestCar2", 100,"Supplier3",6);

        testVehicles.add(testCar);
        testVehicles.add(testCar1);
        testVehicles.add(testCar2);

        Collections.sort(testVehicles, Vehicle.VehiclePriceComparator); // Sort ascending price order
        assertEquals(testCar2, testVehicles.get(0)); // TestCar 2 is cheapest
        assertEquals(testCar1, testVehicles.get(2)); // TestCar 1 is most expensive
    }

    @Test
    public void supplierRatingCorrectOrder() {
        ArrayList<Vehicle> testVehicles = new ArrayList<>();
        Vehicle testCar = new Vehicle("MBMR", "TestCar", 100,"Supplier1",6);
        Vehicle testCar1 = new Vehicle("MBMR", "TestCar1", 100,"Supplier2",7);
        Vehicle testCar2 = new Vehicle("MBMR", "TestCar2", 100,"Supplier3",8);

        testVehicles.add(testCar);
        testVehicles.add(testCar1);
        testVehicles.add(testCar2);

        Collections.sort(testVehicles, Vehicle.VehicleSupplierComparator); // Sort descending supplier order
        assertEquals(testCar2, testVehicles.get(0)); // TestCar 2 has highest supplier rating
    }

    @Test
    public void vehicleScoreCorrectTotal() {
        int supplierRating = 6;
        Vehicle testCar = new Vehicle("MBMR", "TestCar", 100,"Supplier1",supplierRating);

        // Manual transmission – 1 point
        // Air conditioned – 2 points
        // Total Should be 3
        assertTrue(3 == testCar.getVehicleScore());
        assertTrue((3 + supplierRating) == testCar.getTotalScore());
    }

    @Test
    public void totalScoreCorrectOrder() {
        ArrayList<Vehicle> testVehicles = new ArrayList<>();
        Vehicle testCar = new Vehicle("MBMR", "TestCar", 100,"Supplier1",6);
        Vehicle testCar1 = new Vehicle("MBMR", "TestCar1", 100,"Supplier2",7);
        Vehicle testCar2 = new Vehicle("MBMR", "TestCar2", 100,"Supplier3",8);
        testVehicles.add(testCar);
        testVehicles.add(testCar1);
        testVehicles.add(testCar2);

        Collections.sort(testVehicles, Vehicle.VehicleScoreComparator); // Sort descending supplier order
        assertEquals(testCar2, testVehicles.get(0)); // TestCar 2 has highest total rating
    }

    /* Checking sipp to specifications */
    @Test
    public void correctSippToCarType() {
        Vehicle vehicle = new Vehicle("MBMN", "TestCar", 100,"Supplier",6);

        String carType = vehicle.getCarType();

        assertEquals("Mini", carType);
    }
    @Test
    public void correctSipToDoors() {
        Vehicle vehicle = new Vehicle("MBMN", "TestCar", 100,"Supplier",6);

        String doors = vehicle.getDoors();
        assertEquals("2 Doors", doors);
    }
    @Test
    public void correctSipToTransmission() {
        Vehicle vehicle = new Vehicle("MBMN", "TestCar", 100,"Supplier",6);

        String transmission = vehicle.getTransmission();

        assertEquals("Manual", transmission);
    }
    @Test
    public void correctSipToFuel() {
        Vehicle vehicle = new Vehicle("MBMN", "TestCar", 100,"Supplier",6);

        String fuel = vehicle.getFuel();


        assertEquals("Petrol", fuel);
    }
    @Test
    public void correctSipToAirCon() {
        Vehicle vehicle = new Vehicle("MBMN", "TestCar", 100,"Supplier",6);

        String airCon = vehicle.getAirCon();
        assertEquals("No Air Conditioning", airCon);
    }

    @Test
    public void errorCheckingSpec() {
        Vehicle vehicle = new Vehicle("MBMX", "TestCar", 100,"Supplier",6);
        String airCon = vehicle.getAirCon();
        String fuel = vehicle.getFuel();
        // X is not valid 4th letter hence null
        assertEquals(null, airCon);
        assertEquals(null, fuel);
    }


}
