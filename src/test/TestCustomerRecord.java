package test;
import model.*;
import manager.*;

import java.util.Calendar;
import java.util.Date;

/**
 * A test class for the model.CustomerRecord class.
 * This class utilizes the test.Assertions framework to verify the functionality of the model.CustomerRecord class.
 * Test:
 * Behavior of model.CustomerRecord objects: immutability, ID generation, equality logic, and correct data retrieval.
 * @author Fandi Zhou
 */
public class TestCustomerRecord {

    /**
     * The main for the CustomerRecord tests.
     */
    public static void main(String[] args) {
        System.out.println("Running model.CustomerRecord Tests...");

        tCreationAndGetters();//Tests object creation and get method
        System.out.println("Tests object creation and get method: pass!");

        tIDGeneration();
        System.out.println("Auto incrementing ID logic check pass!");

        tEquality();
        System.out.println("Tests the override equal method: pass!");

        testHashCode();
        System.out.println("Tests the override hashcode method: pass!");

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
     * Tests valid object creation and data retrieval methods.
     * Verifies that the getters return exactly what was passed to the constructor.
     */
    public static void tCreationAndGetters() {

        Name name = new Name("Alice", "Smith");
        Date dob = createDate(1995, 5, 20);
        boolean hasLicense = true;

        CustomerRecord record = new CustomerRecord(name, dob, hasLicense);


        Assertions.assertEquals(name, record.getName());// Verify Name

        Assertions.assertEquals(dob, record.getBirthDate());// Verify Date

        Assertions.assertTrue(record.isHaveC_licence());// Verify License status
    }

    /**
     * Tests the auto incrementing ID logic.
     * Verifies that two sequentially created records have unique, increasing IDs.
     */
    public static void tIDGeneration() {

        Name name = new Name("David", "Dan");
        Date dob = createDate(2000, 12, 12);

        CustomerRecord c1 = new CustomerRecord(name, dob, false);
        CustomerRecord c2 = new CustomerRecord(name, dob, false);

        Assertions.assertNotEquals(c1.getCustomerID(), c2.getCustomerID());// Check that IDs are not the same

        boolean isIncrementing = c2.getCustomerID() > c1.getCustomerID();// Check that ID is incrementing
        Assertions.assertTrue(isIncrementing);
    }

    /**
     * Tests the override equal method.
     * Cases:
     * 1. Same object
     * 2. Same content = equal
     * 3. Different content
     * 4. Null check
     * 5. Different class type
     */
    public static void tEquality() {

        Date dob = createDate(1990, 10, 15);
        Name name1 = new Name("John", "Doe");
        Name name2 = new Name("John", "Doe"); // Same content as name1
        Name name3 = new Name("Jane", "Aray");

        CustomerRecord r1 = new CustomerRecord(name1, dob, true);
        CustomerRecord r2 = new CustomerRecord(name2, dob, false); // License different shouldn't affect equality
        CustomerRecord r3 = new CustomerRecord(name3, dob, true); // Different Name
        CustomerRecord r4 = new CustomerRecord(name1, createDate(1991, 12, 1), true); // Different Date

        // Same object reference
        Assertions.assertTrue(r1.equals(r1));

        // Logical equality (Same model.Name + Same dob)
        Assertions.assertTrue(r1.equals(r2));

        // Different Name
        Assertions.assertFalse(r1.equals(r3));

        // Different Date
        Assertions.assertFalse(r1.equals(r4));

        // Null check
        Assertions.assertFalse(r1.equals(null));

        // Different Class type
        Assertions.assertFalse(r1.equals("String String(This is a Sting) not the same class type."));
    }

    /**
     * Tests the override hashCode method.
     * Verifies that two objects that are equal also have the same hashcode.
     */
    public static void testHashCode() {

        Name name = new Name("Jackson", "Wang");
        Date dob = createDate(1999, 8, 18);

        CustomerRecord r1 = new CustomerRecord(name, dob, true);
        CustomerRecord r2 = new CustomerRecord(name, dob, false); // Effectively equal

        Assertions.assertTrue(r1.equals(r2));
        Assertions.assertEquals(r1.hashCode(), r2.hashCode());
    }
}
