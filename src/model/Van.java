package model;

import manager.VehicleID;

/**
 * Class represents a Vehicle of Van.
 * This class extends AbstractVehicle to inherit common vehicle properties.
 * It has unique behaviors specific to vans:
 * Shorter Service Interval:Vans require service every 5000 miles.
 * Inspection Logic:Vans may require a special inspection after long hires.
 * tracked by the needInspection flag.
 *
 * @author Fandi Zhou
 */
public class Van extends AbstractVehicle {

    private boolean needInspection;//indicating whether this van requires a inspection.


    /**
     * Constructs a new Van instance with the specified unique identifier.
     *
     * @param id assigned to this van.
     */
     Van(VehicleID id) {
        super(id);
        this.needInspection = false;
    }

    /**
     * Get method of the string representation of this vehicle's type.
     * @return the string "Van".
     */
    public String getVehicleType() {
        return "Van";
    }

    /**
     * Get the mileage limit before this van requires a service.
     * @return the service interval in miles 5000.
     */
    public int getDistanceRequirement(){
        return 5000;
    }

    /**
     * Method checks if the vehicle requires a special inspection.
     * This overrides the default implementation of model.AbstractVehicle which always returns false.
     * The manager.VehicleManager checks this before allowing the van to be hired.
     * @return true if an inspection is pending and vice versa.
     */
    public boolean requiresInspection(){
        return needInspection;
    }

    /**
     * Sets method og the inspection requirement status for the van.
     * @param needInspection true to flag the van for inspection and vice versa.
     */
    public void setNeedInspection(boolean needInspection) {
        this.needInspection = needInspection;
    }

}
