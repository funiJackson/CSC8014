package test;
import model.*;
import manager.*;

import java.util.*;

/**
 * A test class for the VehicleManager class.
 * This class utilizes the Assertions framework to verify the functionality of the VehicleManager class.
 * It includes test cases for:
 * 1.Normal operations
 * 2.Boundary conditions
 * 3.Exception handling
 * @author Fandi Zhou
 */

public class TestVehicleManager {

    /**
     * The main for the test harness.
     */
    public static void main(String[] args) {
        System.out.println("Running VehicleManager Tests...");

        tAddVehicle();// Tests addVehicle function
        System.out.println("Successfully added vehicle!");

        tAddCustomerRecord(); // Tests addCustomerRecord function
        System.out.println("Successfully tested addCustomerRecord method!");

        tHireVanSuccess();//Tests simple successful Van hiring
        System.out.println("Successfully hire a Van!");
        tHireCarSuccess();//Tests simple successful Car hiring
        System.out.println("Successfully hire a Car!");

        System.out.println("Now testing car hiring logic:");
        tHireCarUnderage();//Tests the car hiring logic
        System.out.println("Pass!");

        System.out.println("Now testing van hiring logic:");
        tHireVanUnderage();//Tests the van hiring logic
        System.out.println("Pass!");

        tHireVanNoLicense();// No C-License to hire van case
        System.out.println("No C-License to hire van case: Pass!");

        System.out.println("Exceed 3 vehicles hired case result: ");
        tHireLimit();// Max 3 vehicles hired case
        System.out.println("Pass!");


        tReturnVehicle();//ReturnVehicle method check
        System.out.println("ReturnVehicle method check pass!");

        System.out.println("Check when hire is not available case result: ");
        tHireNoAvailability();//Tests noOfAvailableVehicles method
        System.out.println("Hire a car when is not available case: pass! ");

        tGetVehiclesByCustomer();//Tests getVechilesByCustomer method
        System.out.println("getVechilesByCustomer method working!");

        System.out.println("All tests passed!");
    }

    /**
     * Helper method to create a Date.
     */
    private static Date createDate(int year, int month, int day) {

        Calendar cal = Calendar.getInstance();//Get an exact time

        cal.clear();//clear the specific time that created

        cal.set(Calendar.YEAR, year);//Set the year

        cal.set(Calendar.MONTH, month - 1);//Set the month

        cal.set(Calendar.DAY_OF_MONTH, day);//Set the day

        return cal.getTime();
    }

    /**
     * Test:
     * 1. Vehicles can be added to the system.
     * 2. The method returns Vehicle addVehicle(String vehicleType)
     * the correct count for different vehicle types.
     */
    public static void tAddVehicle() {
        VehicleManager m = new VehicleManager();
        m.addVehicle("Car");
        m.addVehicle("Car");
        m.addVehicle("Van");

        // Check availability counts
        Assertions.assertEquals(2, m.noOfAvailableVehicles("Car"));
        Assertions.assertEquals(1, m.noOfAvailableVehicles("Van"));
    }


    /**
     * Tests simple successful Car hiring.
     */
    public static void tHireCarSuccess() {
        VehicleManager m = new VehicleManager();

        m.addVehicle("Car");

        CustomerRecord c = m.addCustomerRecord("Liu", "Jack", createDate(2000, 10, 16), false);

        boolean result = m.hireVehicle(c, "Car", 1);

        Assertions.assertTrue(result);
    }

    /**
     * Tests the successful hiring of a Van.
     */
    public static void tHireVanSuccess() {
        VehicleManager m = new VehicleManager();

        m.addVehicle("Van");

        // driver (born 1990, > 23, has license)
        CustomerRecord c = m.addCustomerRecord("Jane", "Arya", createDate(1990, 1, 1), true);

        boolean result = m.hireVehicle(c, "Van", 5);

        Assertions.assertTrue(result);
    }

    /**
     * Tests the age restriction logic for hiring a Car.
     * This is an Exception Testing case. It verifies that an
     * IllegalArgumentException is thrown when a customer under the age of 18
     * attempts to hire a Car.
     */
    public static void tHireCarUnderage() {
        VehicleManager m = new VehicleManager();
        m.addVehicle("Car");

        // Underage customer (10 years old)
        CustomerRecord kid = m.addCustomerRecord("Bob", "David", createDate(2016, 1, 1), false);

        try {
            m.hireVehicle(kid, "Car", 1);
            Assertions.assertNotReached(); // Should not reach here
        } catch (Exception e) {
            // Expect IllegalArgumentException for age < 18
            Assertions.assertExpectedThrowable(IllegalArgumentException.class, e);
        }
    }

    /**
     * Tests 18 <= Age < 23 restriction for Vans.
     */
    public static void tHireVanUnderage() {
        VehicleManager m = new VehicleManager();

        m.addVehicle("Van");
        // Born 2004 (Age 22) OK for Car young for Van
        CustomerRecord young = m.addCustomerRecord("Yong", "Sen", createDate(2004, 1, 1), true);

        try {
            m.hireVehicle(young, "Van", 1);
            Assertions.assertNotReached();
        } catch (Exception e) {
            Assertions.assertExpectedThrowable(IllegalArgumentException.class, e);
        }
    }

    /**
     * Tests the license requirement logic for hiring a Van.
     * This is an Exception Testing case. It verifies that an
     * IllegalArgumentException is thrown when a customer acts as a driver
     * but does not possess a valid commercial license (C-license).
     */
    public static void tHireVanNoLicense() {
        VehicleManager m = new VehicleManager();
        m.addVehicle("Van");

        // Old enough (born 1980) but NO commercial license
        CustomerRecord noLicense = m.addCustomerRecord("J", "Jackson", createDate(1980, 1, 1), false);

        try {
            m.hireVehicle(noLicense, "Van", 1);
            Assertions.assertNotReached();
        } catch (Exception e) {
            Assertions.assertExpectedThrowable(IllegalArgumentException.class, e);
        }
    }

    /**
     * Tests the vehicle hiring limit per customer (Boundary Condition).
     * Test:
     * 1. A customer can hire up to the maximum allowed number of vehicles (3).
     * 2. Attempting to hire a 4th vehicle returns false as expected.
     */
    public static void tHireLimit() {
        VehicleManager m = new VehicleManager();
        // Add cars
        for(int i=0; i<5; i++) m.addVehicle("Car");

        CustomerRecord customer = m.addCustomerRecord("Shawanmi", "Recatar", createDate(1990, 1, 1), false);

        // Hire 3 vehicles (allowed)
        Assertions.assertTrue(m.hireVehicle(customer, "Car", 1));
        Assertions.assertTrue(m.hireVehicle(customer, "Car", 1));
        Assertions.assertTrue(m.hireVehicle(customer, "Car", 1));

        // Try to hire 4th vehicle (Boundary Condition: Max 3)
        boolean fourthHire = m.hireVehicle(customer, "Car", 1);

        // Should return false due to limit check
        Assertions.assertFalse(fourthHire);
    }


    /**
     * Tests the vehicle return process.
     * Test when returnVehicle method is called:
     * 1.The vehicle becomes available again (count increases).
     * 2.The vehicle's hired status is set to changed.
     * 3.The vehicle's mileage is updated correctly.
     */
    public static void tReturnVehicle() {
        VehicleManager m = new VehicleManager();
        Vehicle v = m.addVehicle("Car");
        CustomerRecord c = m.addCustomerRecord("Ryan", "Li", createDate(1990, 1, 1), false);

        m.hireVehicle(c, "Car", 5);
        Assertions.assertEquals(0, m.noOfAvailableVehicles("Car"));

        // Return vehicle with 100 mileage
        m.returnVehicle(v.getVehicleID(), c, 100);

        // model.Vehicle should be available again
        Assertions.assertEquals(1, m.noOfAvailableVehicles("Car"));
        Assertions.assertFalse(v.isHired());
        Assertions.assertEquals(100, v.getCurrentMileage());
    }

    /**
     * Tests addCustomerRecord including duplicate detection.
     */
    public static void tAddCustomerRecord() {
        VehicleManager m = new VehicleManager();

        // Normal addition check
        CustomerRecord c1 = m.addCustomerRecord("John", "Snow", createDate(1990, 12, 13), false);
        Assertions.assertEquals("John", c1.getName().getFirstName());

        // Duplicate addition check
        try {
            m.addCustomerRecord("John", "Snow", createDate(1990, 12, 13), false);
            Assertions.assertNotReached();
        } catch (Exception e) {
            Assertions.assertExpectedThrowable(IllegalArgumentException.class, e);
        }
    }

    /**
     * Tests hiring when no vehicles are available.
     */
    public static void tHireNoAvailability() {

        VehicleManager m = new VehicleManager();

        m.addVehicle("Car"); // Only 1 car in the system now
        CustomerRecord c1 = m.addCustomerRecord("Jackson", "zhou", createDate(2002, 12, 16), false);
        CustomerRecord c2 = m.addCustomerRecord("Lei", "Li", createDate(1999, 1, 1), false);

        m.hireVehicle(c1, "Car", 1); // Takes the only car

        // c2 tries to hire, should return false
        Assertions.assertFalse(m.hireVehicle(c2, "Car", 1));
    }

    /**
     * Tests getVechilesByCustomer method.
     */
    public static void tGetVehiclesByCustomer() {

        VehicleManager m = new VehicleManager();

        m.addVehicle("Car");
        m.addVehicle("Van");
        CustomerRecord c = m.addCustomerRecord("Jackson", "Zhou", createDate(2000, 12, 1), true);

        m.hireVehicle(c, "Car", 1);
        m.hireVehicle(c, "Van", 1);

        Collection<Vehicle> hired = m.getVechilesByCustomer(c);
        Assertions.assertEquals(2, hired.size());
    }
}
