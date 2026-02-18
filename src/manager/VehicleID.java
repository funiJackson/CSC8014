package manager;
import model.*;

import java.util.*;

/**
 * Class represents a unique id for a vehicle in the system.
 * This class uses a Factory pattern to generate unique IDs(with first and second component) with specific formats based on the vehicle type, the first component rules:
 * The first character is a letter that indicates the vehicle type (C for Car, V forVan).
 * The second character is a randomly generated letter.
 * The third character is a randomly generated single-digit number (0-9).
 * The second component rules:
 * A three-digit number, randomly generated to ensure each vehicle ID is distinct. This number must be even for cars and odd for vans.
 * @author Fandi Zhou
 */
public class VehicleID {

    private final String firstComponent;
    private final int secondComponent;
    private final String strRep;

    private static final Map<String, VehicleID> allIDs = new HashMap<>();//A static registry of all generated IDs.

    /**
     * Private constructor to enforce the use of the factory method.
     *
     * @param firstComponent  the prefix component.
     * @param secondComponent the numeric suffix component.
     * @param strRep          the full string representation of Car or Van.
     */
    private VehicleID(String firstComponent, int secondComponent, String strRep) {
        this.firstComponent= firstComponent;
        this.secondComponent = secondComponent;
        this.strRep = strRep;
    }



    /**
     * Method generates a random ID adhering to the format rules for the Car or Van type.
     * @param type the type of vehicle ("Car" or "Van").
     * @return a unique id.
     * @throws IllegalArgumentException if the provided type is invalid.
     */
    public static VehicleID getInstance(String type) {
        Random r= new Random();

        // While loop to ensure we keep looping until we get a unique ID.
        while (true) {

            if(type.equals("Car")) {
                String firstChar = "C";
                char secondChar = (char) ('A' + r.nextInt(26));//Randomly generate letter from A-Z.
                int thirdChar = r.nextInt(10);//Randomly generate letter from 1-9.
                String temp_firstComponent = firstChar + secondChar + thirdChar;//Connect those 3 char we generated.

                int n = r.nextInt(500);//Randomly generate a number from 1-499.
                int temp_secondComponent = n * 2;//Multiply the number we create so the number can always be even.

                String temp_ID = temp_firstComponent + "-" + String.format("%03d", temp_secondComponent);//Creat a temp obj to connect first and second component, make sure generate the correct format

                if(!allIDs.containsKey(temp_ID)) {//Check the generated id if is unique, if it is, put the id in the store map.
                    VehicleID newID = new VehicleID(temp_firstComponent, temp_secondComponent, temp_ID);
                    allIDs.put(temp_ID, newID);

                    return newID;
                }
                //Van id generate case.
            }else if(type.equals("Van")) {
                String firstChar = "V";
                char secondChar = (char) ('A' + r.nextInt(26));
                int thirdChar = r.nextInt(10);
                String temp_firstComponent = firstChar + secondChar + thirdChar;

                int n = r.nextInt(500);
                int temp_secondComponent = n * 2 + 1;// This make sure the number we created can always be odd.

                String temp_ID = temp_firstComponent + "-" + String.format("%03d", temp_secondComponent);//Creat a temp obj to connect first and second component, make sure generate the correct format

                if(!allIDs.containsKey(temp_ID)) {
                    VehicleID newID = new VehicleID(temp_firstComponent, temp_secondComponent, temp_ID);
                    allIDs.put(temp_ID, newID);

                    return newID;
                }
                //throw an exception if the input param is incorrect.
            }else {
                throw new IllegalArgumentException("Invalid vehicle type: " + type);
            }
        }
    }

    /**
     * Gets the first component of the ID.
     * @return the prefix string (e.g., "CA1").
     */
    public String getFirstComponent() {
        return firstComponent;
    }

    /**
     * Gets the second component of the ID.
     * @return the suffix integer.
     */
    public int getSecondComponent() {
        return secondComponent;
    }


    /**
     * Returns the string representation of the ID.
     * @return the formatted ID string (e.g., "CA1-102").
     */
    public String toString() {
        return strRep;
    }

    /**
     * Override equals methods to compares this VehicleID to another object for equality.
     * @param obj the reference object with which to compare.
     * @return true if this object is the same as the obj argument and vice versa.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj){return true;}//Check if there are same object.
        if (obj == null){return false;}//Check if the param we pass is null.
        if (!(obj instanceof VehicleID)){return false;}//Check if they belong to same class.
        VehicleID V = (VehicleID) obj;//Downcasting obj to name so it can compare.

        //Check if they are match.
        return Objects.equals(strRep, V.strRep);
    }

    /**
     * This method is essential for using VehicleID objects in has-based collections
     * It ensures that equal objects produce the same hash code.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(strRep);
    }
}
