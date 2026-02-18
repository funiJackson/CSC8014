package model;

import manager.VehicleID;

/**
 * Class represents a specific class of Car.
 * This class extends model.AbstractVehicle and provides implementations
 * @author Fandi Zhou
 */
public class Car extends AbstractVehicle {

    /**
     * Constructs a new Car instance with the specified unique identifier.
     * This constructor delegates the initialization of the ID, hiring status and mileage to the superclass AbstractVehicle.
     * @param id assigned to this car.
     */
    Car(VehicleID id) {
        super(id);// Call the constructor of the abstract superclass to handle ID assignment.
    }

    /**
     * Returns the string representation of this vehicle's type.
     * @return the string "Car".
     */
    public String getVehicleType() {
        return "Car";
    }

    /**
     * Returns the mileage limit before this car requires a service.
     * According to rules, cars must be serviced every 10,000 miles.
     * @return the service interval in miles 10000.
     */
    public int getDistanceRequirement(){
        return 10000;
    }

}
