import java.util.*;

/**
 * A test suite for the Vehicle Hire Management System.
 * This class utilizes the Assertions framework to verify the functionality
 * of the VehicleManager class.
 * It includes test cases for:
 * 1.Normal operations (Adding vehicles, successful hiring, returning vehicles)
 * 2.Boundary conditions (Hiring limit constraints)
 * 3.Exception handling (Age restrictions, license requirements)
 *
 * @author Fandi Zhou
 */

public class Test {

    /**
     * The main entry point for the test harness.
     */
    public static void main(String[] args) {
        System.out.println("Running VehicleManager Tests...");
        tAddVehicle();
        tHireVanSuccess();
        tHireCarUnderage();
        tHireVanNoLicense();
        tHireLimit();
        tReturnVehicle();
        System.out.println("VehicleManagerTest: All tests passed!");
    }

    /**
     * Helper method to create a Date object from specific year, month, and day values.
     */
    private static Date createDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
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
     * Tests the successful hiring of a Van.
     */
    public static void tHireVanSuccess() {
        VehicleManager m = new VehicleManager();
        m.addVehicle("Van");

        // driver (born 1990, > 23, has license)
        CustomerRecord customer = m.addCustomerRecord("Jane", "Arya", createDate(1990, 1, 1), true);

        boolean success = m.hireVehicle(customer, "Van", 5);

        Assertions.assertTrue(success);
    }

    /**
     * Tests the age restriction logic for hiring a Car.
     * This is an Exception Testing case. It verifies that an
     * {@link IllegalArgumentException} is thrown when a customer under the age of 18
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
     * Tests the license requirement logic for hiring a Van.
     * This is an <b>Exception Testing</b> case. It verifies that an
     * {@link IllegalArgumentException} is thrown when a customer acts as a driver
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

        // Vehicle should be available again
        Assertions.assertEquals(1, m.noOfAvailableVehicles("Car"));
        Assertions.assertFalse(v.isHired());
        Assertions.assertEquals(100, v.getCurrentMileage());
    }
}
