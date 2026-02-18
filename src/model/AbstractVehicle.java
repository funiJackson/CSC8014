package model;

import manager.VehicleID;

/**
 * AbstractVehicle serves as the base class for all specific vehicle types (e.g., Car, Van).
 * It implements the common functionality defined in the Vehicle interface.
 * This class manages the state shared by all vehicles, including:
 * Identity: The unique VehicleID.
 * Hiring Status: Whether the vehicle is currently on hire.
 * Mileage:Tracking the distance traveled to determine service intervals.
 * Specific behaviors, such as the service distance requirement.
 *
 * @author Fandi Zhou
 */

public abstract class AbstractVehicle implements Vehicle {

    private boolean isHired;
    private final VehicleID id;
    private int currentMileage;


    /**
     * Constructs a new model.AbstractVehicle with the given ID.
     * Initializes the vehicle as available (not hired) and with zero mileage.
     * This constructor is package-private as it should only be called by subclasses or the factory method.
     *
     * @param id assigned to this vehicle.
     */
    AbstractVehicle(VehicleID id) {
        this.isHired = false;
        this.currentMileage = 0;
        this.id = id;
    }

    /**
     * Static Factory method to create instances of specific Vehicle types.
     * This method encapsulates the creation logic. It generates a new unique ID
     * It instantiates the appropriate subclass (Car or Van) based on the input string.

     * @param type the type of vehicle to create (e.g., "Car" or "Van").
     * @return a new instance of a  Car or Van subclass.
     * @throws IllegalArgumentException if the provided vehicle type is not recognized.
     */
    public static Vehicle getInstance(String type) {
        VehicleID newID = VehicleID.getInstance(type);// Get a new VehicleID specific to the requested type.

        if (type.equals("Car")) {
            return new Car(newID);
        } else if (type.equals("Van")) {
            return new Van(newID);
        } else {//Handle invalid input types.
            throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }

    }

    /**
     * Returns the unique ID of the vehicle.
     * @return the id object associated with this vehicle.
     */
    public VehicleID getVehicleID() {
        return id;
    }

    /**
     * Method checks if the vehicle is currently hired.
     * @return true or false.
     */
    public boolean isHired() {
        return isHired;
    }

    /**
     * Updates the hiring status of the vehicle.
     * @param h true to mark as hired; false to mark as available.
     */
    public void setHired(boolean h) {
        this.isHired = h;
    }

    /**
     * Returns the distance requirement for servicing this specific type of vehicle.
     * This is an abstract method because different vehicles (Cars vs. Vans) have different service intervals.
     */
    public abstract int getDistanceRequirement();

    /**
     * Gets the current mileage accumulated since the last service.
     * @return the current mileage as an integer.
     */
    public int getCurrentMileage() {
        return currentMileage;
    }

    /**
     * Set method of the current mileage of the vehicle.
     * @param mileage the new mileage value to set.
     */
    public void setCurrentMileage(int mileage) {
        this.currentMileage = mileage;
    }

    /**
     * Checks method if a service is due based on mileage and performs it if necessary.
     * @return true if the service was performed and vice versa.
     */
    public boolean performServiceIfDue() {
        if( this.currentMileage >= getDistanceRequirement()){// Check if the accumulated mileage has reached or exceeded the service limit.
            this.currentMileage = 0;
            return true;
        }
        return false;// No service needed.
    }

    /**
     * Method to indicates whether the vehicle requires a special inspection.
     * By default, vehicles do not require inspection. This method is designed to be overridden by subclasses.
     * @return false by default.
     */
    public boolean requiresInspection() {
        return false;
    }

}
